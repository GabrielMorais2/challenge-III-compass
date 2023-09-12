package com.moraes.gabriel.mscars.service;

import Utils.JsonUtils;
import com.moraes.gabriel.mscars.domain.car.Car;
import com.moraes.gabriel.mscars.domain.car.CarRepository;
import com.moraes.gabriel.mscars.domain.car.CarService;
import com.moraes.gabriel.mscars.domain.car.payload.CarRequest;
import com.moraes.gabriel.mscars.domain.car.payload.CarResponse;
import com.moraes.gabriel.mscars.exception.CarAlreadyExistsException;
import com.moraes.gabriel.mscars.exception.CarNotFoundException;
import com.moraes.gabriel.mscars.exception.PilotAlreadyExistsException;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    @Spy
    private ModelMapper mapper;


    private static final String CAR = "Payload/CAR.json";
    private static final String CAR_RESPONSE = "Payload/CAR_RESPONSE.json";
    private static final String CAR_REQUEST = "Payload/CAR_REQUEST.json";

    @Test
    void createCar_ReturnAnCarResponse() throws IOException {
        Car car = JsonUtils.getObjectFromFile(CAR, Car.class);
        CarResponse carResponse = JsonUtils.getObjectFromFile(CAR_RESPONSE, CarResponse.class);
        CarRequest carRequest = JsonUtils.getObjectFromFile(CAR_REQUEST, CarRequest.class);

        when(carRepository.save(any())).thenReturn(car);

        CarResponse response = carService.createCar(carRequest);

        assertNotNull(response);
        assertEquals(carResponse.getId(), response.getId());
        assertEquals(carResponse.getModel(), response.getModel());
    }

    @Test
    void createCar_WithCarAlreadyExistsException_ReturnAnCarAlreadyExistsException() throws IOException {
        Car car = JsonUtils.getObjectFromFile(CAR, Car.class);
        CarRequest carRequest = JsonUtils.getObjectFromFile(CAR_REQUEST, CarRequest.class);

        when(carRepository.findAll()).thenReturn(List.of(car));

        assertThrows(CarAlreadyExistsException.class, () -> {
            carService.createCar(carRequest);
        });
    }

    @Test
    void createCar_WithPilotAlreadyExistsException_ReturnAnPilotAlreadyExistsException() throws IOException {
        Car car = JsonUtils.getObjectFromFile(CAR, Car.class);
        CarRequest carRequest = JsonUtils.getObjectFromFile(CAR_REQUEST, CarRequest.class);

        car.setModel("new Model");
        car.setBrand("new Brand");

        when(carRepository.findAll()).thenReturn(List.of(car));

        assertThrows(PilotAlreadyExistsException.class, () -> {
            carService.createCar(carRequest);
        });
    }

    @Test
    void getCarById_ReturnAnCarResponse() throws IOException {
        Car car = JsonUtils.getObjectFromFile(CAR, Car.class);
        CarResponse carResponse = JsonUtils.getObjectFromFile(CAR_RESPONSE, CarResponse.class);

        when(carRepository.findById(anyLong())).thenReturn(Optional.of(car));

        CarResponse response = carService.getCarById(1L);

        assertNotNull(response);
        assertEquals(carResponse.getId(), response.getId());
        assertEquals(carResponse.getModel(), response.getModel());
    }

    @Test
    void getAllCars_ReturnAnListOfCarResponse() throws IOException {
        Car car = JsonUtils.getObjectFromFile(CAR, Car.class);

        when(carRepository.findAll()).thenReturn(List.of(car));

        List<CarResponse> response = carService.getAllCars();

        assertNotNull(response);
        assertEquals(car.getId(), response.get(0).getId());
        assertEquals(car.getModel(), response.get(0).getModel());
    }

    @Test
    void deleteCarById_WhenCarExists() throws IOException {
        Car car = JsonUtils.getObjectFromFile(CAR, Car.class);

        when(carRepository.findById(anyLong())).thenReturn(Optional.ofNullable(car));

        carService.deleteCarById(1L);

        verify(carRepository, times(1)).delete(car);
    }

    @Test
    void deleteCar_WithNotFoundCar_ReturnAnCarDoesNotExist() {
        when(carRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CarNotFoundException.class, () -> {carService.deleteCarById(1L);});

        verify(carRepository, never()).delete(any(Car.class));
    }

    @Test
    void updateCar() throws IOException {
        Car car = JsonUtils.getObjectFromFile(CAR, Car.class);
        CarRequest carRequest = JsonUtils.getObjectFromFile(CAR_REQUEST, CarRequest.class);

        Car carUpdate = new Car();
        carUpdate.setBrand("Updated Brand");
        carUpdate.setModel("Updated Model");
        //carUpdate.setYear("2000");

        when(carRepository.findById(anyLong())).thenReturn(Optional.ofNullable(car));
        when(carRepository.save(any(Car.class))).thenReturn(carUpdate);

        CarResponse updatedCarResponse = carService.updateCar(1L, carRequest);

        assertEquals(carUpdate.getBrand(), updatedCarResponse.getBrand());
        assertEquals(carUpdate.getModel(), updatedCarResponse.getModel());

    }
}