package com.moraes.gabriel.msraces.domain.Race;

import Utils.JsonUtils;
import com.moraes.gabriel.msraces.Client.CarResponse;
import com.moraes.gabriel.msraces.Client.CarsFeignClient;
import com.moraes.gabriel.msraces.domain.Track.Track;
import com.moraes.gabriel.msraces.domain.Track.TrackService;
import com.moraes.gabriel.msraces.rabbitmq.RabbitMQMessageProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RacesServiceTest {

    @Mock
    private CarsFeignClient carsFeignClient;

    @Mock
    private RabbitMQMessageProducer messageProducer;

    @InjectMocks
    private RacesService racesService;

    @Mock
    private TrackService trackService;

    @Spy
    private ModelMapper mapper;

    private static final String CAR_RESPONSE = "Payload/races/CAR_RESPONSE.json";
    private static final String RACES_REQUEST = "Payload/races/RACES_REQUEST.json";
    private static final String RACE_RESULT_RESPONSE = "Payload/races/RACE_RESULT_RESPONSE.json";
    private static final String TRACK = "Payload/track/TRACK.json";

    @Test
    void testGetRandomCarsForRace() throws IOException {
        CarResponse[] carResponse = JsonUtils.getObjectFromFile(CAR_RESPONSE, CarResponse[].class);

        when(carsFeignClient.getAllCars()).thenReturn(List.of(carResponse));

        List<CarResponse> randomCars = racesService.getRandomCarsForRace(2);

        assertEquals(2, randomCars.size());

        verify(carsFeignClient, times(1)).getAllCars();
    }

    @Test
    void runRaces() throws IOException {
        RaceRequest raceRequest = JsonUtils.getObjectFromFile(RACES_REQUEST, RaceRequest.class);
        Track track = JsonUtils.getObjectFromFile(TRACK, Track.class);

        when(trackService.getTrackById(anyString())).thenReturn(track);

        doNothing().when(messageProducer).publish(any());

        racesService.runRaces(raceRequest);

        verify(messageProducer, times(1)).publish(any());

    }
}