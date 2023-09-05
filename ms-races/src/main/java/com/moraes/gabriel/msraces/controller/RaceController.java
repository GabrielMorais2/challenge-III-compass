package com.moraes.gabriel.msraces.controller;

import com.moraes.gabriel.msraces.cars.CarResponse;
import com.moraes.gabriel.msraces.service.RacesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/races")
public class RaceController {

    private final RacesService racesService;
    @GetMapping()
    public ResponseEntity<List<CarResponse>> getAllCars() {
        return new ResponseEntity<>(racesService.getRandomCarsForRace(), HttpStatus.OK);
    }
}
