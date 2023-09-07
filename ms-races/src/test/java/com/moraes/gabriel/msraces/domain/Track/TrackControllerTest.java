package com.moraes.gabriel.msraces.domain.Track;

import Utils.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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

    private static final String TRACK_REQUEST = "Payload/track/TRACK_REQUEST.json";
    private static final String TRACK_RESPONSE = "Payload/track/TRACK.json";
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
}