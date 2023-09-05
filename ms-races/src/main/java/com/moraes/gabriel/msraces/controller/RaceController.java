package com.moraes.gabriel.msraces.controller;

import com.moraes.gabriel.msraces.cars.CarResponse;
import com.moraes.gabriel.msraces.model.payload.RaceResultResponse;
import com.moraes.gabriel.msraces.service.RacesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/start-races/{idTrack}")
    public ResponseEntity<RaceResultResponse> startRaces(@PathVariable Long idTrack){
        return new ResponseEntity<>(racesService.startRaces(idTrack), HttpStatus.CREATED);
    }
}
