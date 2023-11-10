package com.w2.canal.listener;


import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.w2.canal.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author zqf
 * @desc
 */

// @Component
@Slf4j
public class CanalListener {

    private CanalConnector conn;
    private RabbitTemplate rabbitTemplate;

    @Bean
    public CanalConnector connector() {
        log.info("初始化");
        CanalConnector connector = CanalConnectors.
                newSingleConnector(
                        new InetSocketAddress("43.143.254.110", 11111),
                        "postdb",
                        null,
                        null);
        connector.connect();
        return connector;
    }


    @PreDestroy
    public void preDestroy() {
        log.info("销毁");
        conn.disconnect();
    }


    /**
     * 解析数据
     *
     * @param beforeColumns 修改、删除后的数据
     * @param afterColumns  新增、修改、删除前的数据
     * @param dbName        数据库名字
     * @param tableName     表大的名字
     * @param eventType     操作类型（INSERT,UPDATE,DELETE）
     * @param timestamp     消耗时间
     */
    private void dataDetails(List<CanalEntry.Column> beforeColumns, List<CanalEntry.Column> afterColumns, String dbName, String tableName, CanalEntry.EventType eventType, long timestamp) {
        if ("find_like_post".equals(dbName) && "t_post".equals(tableName)) {
            String beforeAudit = null, afterAudit=null,id=null;
            for (CanalEntry.Column beforeColumn : beforeColumns) {
                if (beforeColumn.getName().equals("is_audit")) {
                    beforeAudit = beforeColumn.getValue();
                }
            }
            for (CanalEntry.Column afterColumn : afterColumns) {
                if (afterColumn.getName().equals("id")) {
                    id = afterColumn.getValue();
                }
                if (afterColumn.getName().equals("is_audit")) {
                    afterAudit = afterColumn.getValue();
                }
            }
            // 封禁
            if ("1".equals(beforeAudit) && "0".equals(afterAudit)) {
                rabbitTemplate.convertAndSend(RabbitMQConfig.POST_DOWN_EXCHANGE, "", id);
            } else if ("0".equals(beforeAudit) && "1".equals(afterAudit)) {
                // 上架
                rabbitTemplate.convertAndSend(RabbitMQConfig.POST_UP_EXCHANGE, "", id);
            }

        }

        /*System.out.println("数据库：" + dbName);
        System.out.println("表名：" + tableName);
        System.out.println("操作类型:" + eventType);
        if (CanalEntry.EventType.INSERT.equals(eventType)) {
            System.out.println("这是一条新增的数据");
        } else if (CanalEntry.EventType.DELETE.equals(eventType)) {
            System.out.println("删除数据：" + afterColumns);
        } else {
            System.out.println("更新数据：更新前数据--" + afterColumns);
            System.out.println("更新数据：更新后数据--" + beforeColumns);

        }
        System.out.println("操作时间：" + timestamp);
        System.out.println("============================================");*/
    }


    /**
     * 每秒执行一次
     */
    @Scheduled(cron = "* * * * * *")
    public void start() throws Exception {
        log.info("订阅");
        conn.subscribe(".*\\..*");
        // 回滚到未进行ack的地方
        conn.rollback();
        // 获取数据 每次获取一百条改变数据
        Message message = conn.getWithoutAck(100);
        //获取这条消息的id
        long id = message.getId();
        int size = message.getEntries().size();
        if (id != -1 && size > 0) {
            // 数据解析
            analysis(message.getEntries());
        } else {
            //暂停1秒防止重复链接数据库
            Thread.sleep(1000);
        }
        // 确认消费完成这条消息
        conn.ack(message.getId());
    }

    /**
     * 数据解析
     */
    private void analysis(List<CanalEntry.Entry> entries) {
        for (CanalEntry.Entry entry : entries) {
            // 解析binlog
            CanalEntry.RowChange rowChange = null;
            try {
                rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new RuntimeException("解析出现异常 data:" + entry.toString(), e);
            }
            if (rowChange != null) {
                // 获取操作类型
                CanalEntry.EventType eventType = rowChange.getEventType();
                // 获取当前操作所属的数据库
                String dbName = entry.getHeader().getSchemaName();
                // 获取当前操作所属的表
                String tableName = entry.getHeader().getTableName();
                // 事务提交时间
                long timestamp = entry.getHeader().getExecuteTime();
                for (CanalEntry.RowData rowData : rowChange.getRowDatasList()) {
                    List<CanalEntry.Column> beforeColumnsList = rowData.getBeforeColumnsList();
                    dataDetails(beforeColumnsList, rowData.getAfterColumnsList(), dbName, tableName, eventType, timestamp);
                }
            }
        }
    }

}

