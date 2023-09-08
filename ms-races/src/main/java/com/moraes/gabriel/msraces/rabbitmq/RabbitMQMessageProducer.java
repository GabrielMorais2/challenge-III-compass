package com.moraes.gabriel.msraces.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class RabbitMQMessageProducer{

    private final RabbitTemplate rabbitTemplate;
    private final Queue queueSendRaceResult;

    public void publish(Object response) throws JsonProcessingException {
        var json = convertionIntoJson(response);
        log.info("Publishing to {}. Payload: {}", queueSendRaceResult, response);
        rabbitTemplate.convertAndSend(queueSendRaceResult.getName(), json);
        log.info("Published to {}. Payload: {}", queueSendRaceResult, response);
    }

    private String convertionIntoJson(Object response) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(response);
    }
}
