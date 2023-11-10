package com.w2.canal.listener;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.w2.canal.config.RabbitMQConfig;
import com.xpand.starter.canal.annotation.CanalEventListener;
import com.xpand.starter.canal.annotation.ListenPoint;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

@CanalEventListener
public class CanalStarterListener {
    @Autowired
    private RabbitTemplate rabbitTemplate;


    @ListenPoint(schema = "find_link_post",table = "t_post")
    public void petsDetailChangeListener(CanalEntry.EventType eventType, CanalEntry.RowData rowData){
        System.out.println("宠物表数据发生改变");
        //  获取改变前的数据并将这部分转为map
        Map<String, String> oldData = new HashMap<>();
        rowData.getBeforeColumnsList().forEach((c)->{
            oldData.put(c.getName(), c.getValue());
        });
        // 获取改变后的数据并将这部分转为map
        Map<String, String> newData = new HashMap<>();
        rowData.getAfterColumnsList().forEach((c)->{
            newData.put(c.getName(), c.getValue());
        });

        // 封禁
        if ("1".equals(oldData.get("audited")) && "0".equals(newData.get("audited"))) {
            rabbitTemplate.convertAndSend(RabbitMQConfig.POST_DOWN_EXCHANGE, "", newData.get("id"));
        } else if ("0".equals(oldData.get("audited")) && "1".equals(newData.get("audited"))) {
            // 上架
            rabbitTemplate.convertAndSend(RabbitMQConfig.POST_UP_EXCHANGE, "", newData.get("id"));
        }

    }
}
