package com.moraes.gabriel.msraces.domain.Track;

import Utils.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moraes.gabriel.msraces.domain.Track.payload.TrackRequest;
import com.moraes.gabriel.msraces.domain.Track.payload.TrackResponse;
import org.junit.jupiter.api.Test;
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

@WebMvcTest(TrackController.class)
class TrackControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TrackService trackService;

    private static final String TRACK_REQUEST = "/Payload/track/TRACK_REQUEST.json";
    private static final String TRACK_RESPONSE = "/Payload/track/TRACK.json";
    @Test
    void createTrack_WithDataValid_ReturnCreate() throws Exception {
        TrackRequest trackRequest = JsonUtils.getObjectFromFile(TRACK_REQUEST, TrackRequest.class);
        TrackResponse trackResponse = JsonUtils.getObjectFromFile(TRACK_RESPONSE, TrackResponse.class);

        when(trackService.createTrack(trackRequest)).thenReturn(trackResponse);

        mockMvc.perform(post("/v1/tracks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(trackRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(trackRequest.getName()))
                .andExpect(jsonPath("$.country").value(trackRequest.getCountry()));

        verify(trackService).createTrack(trackRequest);
    }

    @Test
    void getTrackById_ReturnAnTrackResponse() throws Exception {
        TrackResponse response = JsonUtils.getObjectFromFile(TRACK_RESPONSE, TrackResponse.class);

        when(trackService.getTrackResponseById(any())).thenReturn(response);

        mockMvc.perform(get("/v1/tracks/{id}", "64ffe621b02d9a02982b9661"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.getId()))
                .andExpect(jsonPath("$.name").value(response.getName()));
    }

    @Test
    void getAllTracks_ReturnAnListOfTracksResponse() throws Exception {
        TrackResponse response = JsonUtils.getObjectFromFile(TRACK_RESPONSE, TrackResponse.class);

        when(trackService.getAllTracks()).thenReturn(List.of(response));

        mockMvc.perform(get("/v1/tracks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(response.getId()))
                .andExpect(jsonPath("$[0].name").value(response.getName()));
    }
}