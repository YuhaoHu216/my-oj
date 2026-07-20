package space.huyuhao.myojjudge.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ 初始化配置：创建交换机和队列
 */
@Slf4j
@Configuration
public class InitRabbitMq {

    @Bean
    public DirectExchange codeExchange() {
        return new DirectExchange("code_exchange");
    }

    @Bean
    public Queue codeQueue() {
        return new Queue("code_queue", true);
    }

    @Bean
    public Binding codeBinding() {
        return BindingBuilder.bind(codeQueue()).to(codeExchange()).with("my_routingKey");
    }
}
