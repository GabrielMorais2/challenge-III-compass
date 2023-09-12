package com.moraes.gabriel.msraces.domain.Race;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.moraes.gabriel.msraces.domain.Race.payload.RaceResultResponse;
import com.moraes.gabriel.msraces.rabbitmq.RabbitMQMessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RaceResultPublisher {

    private final RabbitMQMessageProducer messageProducer;

    public void publishRaceResult(Race race) {
        RaceResultResponse raceResultResponse = createRaceResultResponse(race);
        try {
            messageProducer.publish(raceResultResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private RaceResultResponse createRaceResultResponse(Race race) {
        return RaceResultResponse.builder()
                .cars(race.getCars())
                .dateRace(race.getDate())
                .name(race.getName())
                .track(race.getTrack())
                .status(race.getStatus())
                .build();
    }
}
