package com.moraes.gabriel.mscars.controller;

import Utils.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moraes.gabriel.mscars.model.payload.CarRequest;
import com.moraes.gabriel.mscars.model.payload.CarResponse;
import com.moraes.gabriel.mscars.model.payload.PilotRequest;
import com.moraes.gabriel.mscars.service.CarService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarController.class)
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CarService carService;

    private static final String CAR_RESPONSE = "Payload/CAR_RESPONSE.json";
    private static final String CAR_REQUEST = "Payload/CAR_REQUEST.json";

    @Test
    void createCar_WithValidData_ReturnCreated() throws Exception {
        CarResponse carResponse = JsonUtils.getObjectFromFile(CAR_RESPONSE, CarResponse.class);
        CarRequest carRequest = JsonUtils.getObjectFromFile(CAR_REQUEST, CarRequest.class);

        when(carService.createCar(carRequest)).thenReturn(carResponse);

        mockMvc.perform(post("/api/v1/cars/create-car")
                        .content(objectMapper.writeValueAsString(carResponse))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(carResponse.getId()))
                .andExpect(jsonPath("$.brand").value(carResponse.getBrand()))
                .andExpect(jsonPath("$.model").value(carResponse.getModel()))
                .andExpect(jsonPath("$.year").value(carResponse.getYear()));
    }

    @Test
    void createCar_withEmptyData_ReturnBadRequest() throws Exception {
        CarRequest emptyCoordinatorDtoRequest = new CarRequest("", "", new PilotRequest(), "");

        mockMvc.perform(post("/api/v1/cars/create-car")
                        .content(objectMapper.writeValueAsString(emptyCoordinatorDtoRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getCarById() throws Exception {
        CarResponse carResponse = JsonUtils.getObjectFromFile(CAR_RESPONSE, CarResponse.class);

        when(carService.getCarById(anyLong())).thenReturn(carResponse);

        mockMvc.perform(get("/api/v1/cars/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(carResponse.getId()))
                .andExpect(jsonPath("$.brand").value(carResponse.getBrand()))
                .andExpect(jsonPath("$.model").value(carResponse.getModel()))
                .andExpect(jsonPath("$.year").value(carResponse.getYear()));
    }

    @Test
    void getAllCars() throws Exception {
        CarResponse carResponse = JsonUtils.getObjectFromFile(CAR_RESPONSE, CarResponse.class);

        when(carService.getAllCars()).thenReturn(List.of(carResponse));

        mockMvc.perform(get("/api/v1/cars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(carResponse.getId()))
                .andExpect(jsonPath("$[0].brand").value(carResponse.getBrand()))
                .andExpect(jsonPath("$[0].model").value(carResponse.getModel()))
                .andExpect(jsonPath("$[0].year").value(carResponse.getYear()));
    }

    @Test
    void deleteCarById() throws Exception {
        doNothing().when(carService).deleteCarById(anyLong());

        mockMvc.perform(delete("/api/v1/cars/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateCar() throws Exception {
        CarRequest carRequest = JsonUtils.getObjectFromFile(CAR_REQUEST, CarRequest.class);
        CarResponse carResponse = JsonUtils.getObjectFromFile(CAR_RESPONSE, CarResponse.class);

        when(carService.updateCar(1L, carRequest)).thenReturn(carResponse);

        mockMvc.perform(put("/api/v1/cars/{id}", 1L)
                        .content(asJsonString(carRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}