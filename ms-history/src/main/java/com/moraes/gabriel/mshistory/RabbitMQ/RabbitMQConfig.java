package com.moraes.gabriel.mshistory.RabbitMQ;

import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Queue;
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
