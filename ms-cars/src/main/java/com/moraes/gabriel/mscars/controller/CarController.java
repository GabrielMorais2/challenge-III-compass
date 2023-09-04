package com.moraes.gabriel.mscars.controller;

import com.moraes.gabriel.mscars.model.payload.CarRequest;
import com.moraes.gabriel.mscars.model.payload.CarResponse;
import com.moraes.gabriel.mscars.service.CarService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class CarController {

    private final CarService carService;
    @PostMapping("/create-car")
    public ResponseEntity<CarResponse> createCar(@Valid @RequestBody CarRequest carRequest){
        return new ResponseEntity<>(carService.createCar(carRequest), HttpStatus.CREATED);
    }
}
