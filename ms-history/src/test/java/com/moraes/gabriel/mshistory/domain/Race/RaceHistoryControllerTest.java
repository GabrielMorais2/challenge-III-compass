package com.moraes.gabriel.mshistory.domain.Race;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moraes.gabriel.mshistory.Utils.JsonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RaceHistoryController.class)
class RaceHistoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RaceHistoryService raceHistoryService;

    private static final String RACE_RESPONSE = "Payload/RACE_RESPONSE.json";

    @Test
    void getAllRaceData() throws Exception {
        RaceResponse raceResponse = JsonUtils.getObjectFromFile(RACE_RESPONSE, RaceResponse.class);

        when(raceHistoryService.getAllRaceData()).thenReturn(List.of(raceResponse));

        mockMvc.perform(get("/v1/history/races")
                    .content(objectMapper.writeValueAsString(raceResponse))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(raceResponse.getId()))
                .andExpect(jsonPath("$[0].name").value(raceResponse.getName()));
    }

    @Test
    void getRaceDataById() throws Exception {
        RaceResponse raceResponse = JsonUtils.getObjectFromFile(RACE_RESPONSE, RaceResponse.class);

        when(raceHistoryService.getRaceDataById(anyString())).thenReturn(raceResponse);

        mockMvc.perform(get("/v1/history/races/{id}", 1L)
                    .content(objectMapper.writeValueAsString(raceResponse))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(raceResponse.getId()))
                .andExpect(jsonPath("$.name").value(raceResponse.getName()));
    }
}