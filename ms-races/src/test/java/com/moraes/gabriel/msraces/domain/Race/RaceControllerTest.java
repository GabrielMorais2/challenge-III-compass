package com.moraes.gabriel.msraces.domain.Race;

import Utils.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moraes.gabriel.msraces.domain.Race.payload.RaceRequest;
import com.moraes.gabriel.msraces.domain.Race.payload.RaceResponse;
import com.moraes.gabriel.msraces.domain.Track.TrackService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RaceController.class)
class RaceControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RacesService racesService;

    @Mock
    private TrackService trackService;

    private static final String RACES_REQUEST = "Payload/races/RACES_REQUEST.json";
    private static final String RACES_RESPONSE = "Payload/races/RACES_RESPONSE.json";
    @Test
    void startRace_WithDataValid_ReturnOk() throws Exception {
        RaceRequest raceRequest = JsonUtils.getObjectFromFile(RACES_REQUEST, RaceRequest.class);

        mockMvc.perform(post("/v1/races/run/{id}", "64ffe621b02d9a02982b9661")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(raceRequest)))
                .andExpect(status().isOk());

        verify(racesService).runRaces(any());
    }

    @Test
    void createRace_WithDataValid_ReturnCreate() throws Exception {
        RaceRequest raceRequest = JsonUtils.getObjectFromFile(RACES_REQUEST, RaceRequest.class);
        RaceResponse response = JsonUtils.getObjectFromFile(RACES_RESPONSE, RaceResponse.class);

        when(trackService.getTrackById(any())).thenReturn(response.getTrack());
        when(racesService.createRaces(any())).thenReturn(response);

        mockMvc.perform(post("/v1/races/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(raceRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(response.getName()));
    }

    @Test
    void getRaceById_ReturnAnRaceResponse() throws Exception {
        RaceResponse response = JsonUtils.getObjectFromFile(RACES_RESPONSE, RaceResponse.class);

        when(racesService.getRaceById(any())).thenReturn(response);

        mockMvc.perform(get("/v1/races/{id}", "64ffe621b02d9a02982b9661"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.getId()))
                .andExpect(jsonPath("$.name").value(response.getName()));
    }

    @Test
    void getAllRaces_ReturnAnListOfRacesResponse() throws Exception {
        RaceResponse response = JsonUtils.getObjectFromFile(RACES_RESPONSE, RaceResponse.class);

        when(racesService.getAllRaces()).thenReturn(List.of(response));

        mockMvc.perform(get("/v1/races"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(response.getId()))
                .andExpect(jsonPath("$[0].name").value(response.getName()));
    }


}