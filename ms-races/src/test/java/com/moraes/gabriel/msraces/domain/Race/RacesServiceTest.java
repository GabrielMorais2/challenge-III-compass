package com.moraes.gabriel.msraces.domain.Race;

import Utils.JsonUtils;
import com.moraes.gabriel.msraces.domain.Race.payload.RaceRequest;
import com.moraes.gabriel.msraces.domain.Race.payload.RaceResponse;
import com.moraes.gabriel.msraces.domain.Track.Track;
import com.moraes.gabriel.msraces.domain.Track.TrackService;
import com.moraes.gabriel.msraces.domain.car.CarService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RacesServiceTest {

    @Mock
    private RaceSimulationService raceSimulationService;

    @Mock
    private RaceResultPublisher raceResultPublisher;

    @InjectMocks
    private RacesService racesService;

    @Mock
    private TrackService trackService;

    @Mock
    private CarService carService;

    @Mock
    private RaceRepository raceRepository;

    @Spy
    private ModelMapper mapper;


    private static final String RACES_REQUEST = "/Payload/races/RACES_REQUEST.json";
    private static final String RACES_RESPONSE = "/Payload/races/RACES_RESPONSE.json";
    private static final String TRACK = "/Payload/track/TRACK.json";

    @Test
    void createRace_ReturnAnRaceResponse() throws IOException {
        RaceRequest raceRequest = JsonUtils.getObjectFromFile(RACES_REQUEST, RaceRequest.class);
        Race race = JsonUtils.getObjectFromFile(RACES_RESPONSE, Race.class);
        Track track = JsonUtils.getObjectFromFile(TRACK, Track.class);

        when(trackService.getTrackById(anyString())).thenReturn(track);
        when(carService.getRandomCarsForRace()).thenReturn(race.getCars());
        when(raceRepository.save(any())).thenReturn(race);

        RaceResponse raceResponse = racesService.createRaces(raceRequest);

        assertNotNull(raceResponse);
        assertEquals(race.getId(), raceResponse.getId());
        assertEquals(race.getName(), raceResponse.getName());
    }


    @Test
    void runRaces() throws IOException {
        Race race = JsonUtils.getObjectFromFile(RACES_RESPONSE, Race.class);

        when(raceRepository.findById(any())).thenReturn(Optional.ofNullable(race));

        doNothing().when(raceResultPublisher).publishRaceResult(any());
        doNothing().when(raceSimulationService).runRaces(any());

        assert race != null;
        racesService.runRaces(race.getId());
    }

    @Test
    void getRaceById_ReturnAnRaceResponse() throws IOException {
        Race race = JsonUtils.getObjectFromFile(RACES_RESPONSE, Race.class);
        RaceResponse raceResponse = JsonUtils.getObjectFromFile(RACES_RESPONSE, RaceResponse.class);

        when(raceRepository.findById(any())).thenReturn(Optional.of(race));

        RaceResponse response = racesService.getRaceById(any());

        assertNotNull(response);
        assertEquals(raceResponse.getId(), response.getId());
        assertEquals(raceResponse.getName(), response.getName());
    }

    @Test
    void getAllRaces_ReturnAnListOfRaceResponse() throws IOException {
        Race race = JsonUtils.getObjectFromFile(RACES_RESPONSE, Race.class);

        when(raceRepository.findAll()).thenReturn(List.of(race));

        List<RaceResponse> response = racesService.getAllRaces();

        assertNotNull(response);
        assertEquals(race.getId(), response.get(0).getId());
        assertEquals(race.getName(), response.get(0).getName());
    }
}