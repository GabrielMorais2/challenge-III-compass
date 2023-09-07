package com.moraes.gabriel.msraces.domain.Race;

import Utils.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RaceController.class)
class RaceControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RacesService racesService;

    private static final String RACES_REQUEST = "Payload/races/RACES_REQUEST.json";
    @Test
    void startRace_WithDataValid_ReturnCreate() throws Exception {
        RaceRequest raceRequest = JsonUtils.getObjectFromFile(RACES_REQUEST, RaceRequest.class);

        mockMvc.perform(post("/v1/races/start")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(raceRequest)))
                .andExpect(status().isCreated());

        verify(racesService).runRaces(any());
    }

}