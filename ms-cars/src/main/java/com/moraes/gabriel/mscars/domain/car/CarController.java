package com.moraes.gabriel.mscars.domain.car;

import com.moraes.gabriel.mscars.domain.car.payload.CarRequest;
import com.moraes.gabriel.mscars.domain.car.payload.CarResponse;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(
            summary = "Create a New Car",
            description = "Create a new car with the provided data in the request body."
    )
    @PostMapping
    public ResponseEntity<CarResponse> createCar(@Valid @RequestBody CarRequest carRequest){
        return new ResponseEntity<>(carService.createCar(carRequest), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get a Car by ID",
            description = "Retrieve details of a specific car based on its ID."
    )@GetMapping("/{id}")
    public ResponseEntity<CarResponse> getCarById(@PathVariable Long id) {
        return new ResponseEntity<>(carService.getCarById(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Get All Cars",
            description = "Retrieve a list of all available cars."
    )@GetMapping
    public ResponseEntity<List<CarResponse>> getAllCars() {
        return new ResponseEntity<>(carService.getAllCars(), HttpStatus.OK);
    }

    @Operation(
            summary = "Delete a Car by ID",
            description = "Delete a specific car based on its ID."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarById(@PathVariable Long id) {
        carService.deleteCarById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Update a Car by ID",
            description = "Update the details of a specific car based on its ID."
    )
    @PutMapping("/{id}")
    public ResponseEntity<CarResponse> updateCar(@PathVariable Long id, @Valid @RequestBody CarRequest carRequest) {
        CarResponse updatedCar = carService.updateCar(id, carRequest);
        return new ResponseEntity<>(updatedCar, HttpStatus.OK);
    }

}
