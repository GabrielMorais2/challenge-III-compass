package com.moraes.gabriel.mscars.controller;

import com.moraes.gabriel.mscars.model.payload.CarRequest;
import com.moraes.gabriel.mscars.model.payload.CarResponse;
import com.moraes.gabriel.mscars.service.CarService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/cars")
@AllArgsConstructor
public class CarController {

    private final CarService carService;
    @PostMapping
    public ResponseEntity<CarResponse> createCar(@Valid @RequestBody CarRequest carRequest){
        return new ResponseEntity<>(carService.createCar(carRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarResponse> getCarById(@PathVariable Long id) {
        return new ResponseEntity<>(carService.getCarById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CarResponse>> getAllCars() {
        return new ResponseEntity<>(carService.getAllCars(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarById(@PathVariable Long id) {
        carService.deleteCarById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarResponse> updateCar(@PathVariable Long id, @Valid @RequestBody CarRequest carRequest) {
        CarResponse updatedCar = carService.updateCar(id, carRequest);
        return new ResponseEntity<>(updatedCar, HttpStatus.OK);
    }

}
