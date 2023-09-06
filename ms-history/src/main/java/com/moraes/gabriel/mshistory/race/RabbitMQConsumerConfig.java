package com.moraes.gabriel.mshistory.race;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moraes.gabriel.mshistory.race.model.CarResponse;
import com.moraes.gabriel.mshistory.race.model.RaceResultResponse;
import com.moraes.gabriel.mshistory.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Configuration
@EnableRabbit
@RequiredArgsConstructor
public class RabbitMQConsumerConfig {

    private final HistoryService historyService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "SEND-RACE-RESULT-RESPONSE")
    public void receiveRaceData(String carResponse) throws IOException {
        List<CarResponse> carResponses = objectMapper.readValue(carResponse, new TypeReference<List<CarResponse>>() {});
        historyService.saveRaceData(carResponses);
    }


}