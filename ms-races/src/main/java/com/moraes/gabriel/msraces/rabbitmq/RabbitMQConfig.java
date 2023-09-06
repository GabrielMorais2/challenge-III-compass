package com.moraes.gabriel.msraces.rabbitmq;

import lombok.AllArgsConstructor;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class RabbitMQConfig {
    @Bean
    public Queue queueSendRaceResult() {
        return new Queue("SEND-RACE-RESULT-RESPONSE", true);
    }

}
