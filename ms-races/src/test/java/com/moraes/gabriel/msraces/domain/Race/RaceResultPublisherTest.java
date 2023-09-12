package com.moraes.gabriel.msraces.domain.Race;

import Utils.JsonUtils;
import com.moraes.gabriel.msraces.domain.Race.payload.RaceResultResponse;
import com.moraes.gabriel.msraces.rabbitmq.RabbitMQMessageProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class RaceResultPublisherTest {

    @Mock
    private RabbitMQMessageProducer messageProducer;

    @InjectMocks
    private RaceResultPublisher raceResultPublisher;

    private static final String RACES_RESPONSE = "Payload/races/RACES_RESPONSE.json";

    @Test
    void publishRaceResult() throws IOException {
        Race race = JsonUtils.getObjectFromFile(RACES_RESPONSE, Race.class);

        RaceResultResponse expectedResponse = RaceResultResponse.builder()
                .cars(race.getCars())
                .dateRace(race.getDate())
                .name(race.getName())
                .track(race.getTrack())
                .status(race.getStatus())
                .build();

        doNothing().when(messageProducer).publish(expectedResponse);

        raceResultPublisher.publishRaceResult(race);

        verify(messageProducer).publish(expectedResponse);
    }
}