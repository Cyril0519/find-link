package com.w2.canal.config;


import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    // 交换机名称
    public static final String POST_UP_EXCHANGE = "post_up_exchange";
    public static final String POST_DOWN_EXCHANGE = "post_down_exchange";
    // 定义队列名称
    public static final String POST_LIFT_QUEUE = "post_lift_queue";    // 审核通过消息队列 （audited：0-->1）
    public final static String POST_LOCKDOWN_QUEUE = "post_lockdown_queue"; // 封禁消息队列 （修改删除）

    @Bean(POST_LIFT_QUEUE)
    public Queue POST_LIFT_QUEUE() {
        return new Queue(POST_LIFT_QUEUE);
    }

    @Bean(POST_LOCKDOWN_QUEUE)
    public Queue POST_LOCKDOWN_QUEUE() {
        return new Queue(POST_LOCKDOWN_QUEUE);
    }

    @Bean(POST_UP_EXCHANGE)
    public Exchange PETS_DOWN_EXCHANGE() {
        return ExchangeBuilder.fanoutExchange(POST_UP_EXCHANGE).durable(true).build();
    }

    @Bean(POST_DOWN_EXCHANGE)
    public Exchange PETS_UP_EXCHANGE() {
        return ExchangeBuilder.fanoutExchange(POST_DOWN_EXCHANGE).durable(true).build();
    }



    @Bean
    public Binding PETS_UP_EXCHANGE_BIND(@Qualifier(POST_LIFT_QUEUE) Queue queue, @Qualifier(POST_UP_EXCHANGE) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("").noargs();
    }

    @Bean
    public Binding PETS_DOWN_EXCHANGE_BIND(@Qualifier(POST_LOCKDOWN_QUEUE) Queue queue, @Qualifier(POST_DOWN_EXCHANGE) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("").noargs();
    }

}
