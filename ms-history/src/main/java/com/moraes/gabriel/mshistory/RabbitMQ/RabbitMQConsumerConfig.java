package com.moraes.gabriel.mshistory.RabbitMQ;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moraes.gabriel.mshistory.domain.Race.RaceHistoryService;
import com.moraes.gabriel.mshistory.domain.Race.RaceResultResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@EnableRabbit
@RequiredArgsConstructor
@Slf4j
public class RabbitMQConsumerConfig {

    private final RaceHistoryService raceHistoryService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "SEND-RACE-RESULT-RESPONSE")
    public void receiveRaceData(String raceResultResponse) throws IOException {
        log.info("RECEIVING THE DATA");
        log.info(raceResultResponse);
        RaceResultResponse raceResultResponseByMsRaces = objectMapper.readValue(raceResultResponse, RaceResultResponse.class );
        log.info("MAPPING THE DATA");
        raceHistoryService.saveRaceData(raceResultResponseByMsRaces);
        log.info("DATA SENT TO THE DATABASE");
    }


}