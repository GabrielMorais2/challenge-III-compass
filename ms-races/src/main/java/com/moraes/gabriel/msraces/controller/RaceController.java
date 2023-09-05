package com.moraes.gabriel.msraces.controller;

import com.moraes.gabriel.msraces.cars.CarResponse;
import com.moraes.gabriel.msraces.model.payload.RaceRequest;
import com.moraes.gabriel.msraces.model.payload.RaceResponse;
import com.moraes.gabriel.msraces.model.payload.RaceResultResponse;
import com.moraes.gabriel.msraces.service.RacesService;
import jakarta.validation.Valid;
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

    @PostMapping("/create-races")
    public ResponseEntity<RaceResponse> startRaces(@Valid @RequestBody RaceRequest raceRequest){
        return new ResponseEntity<>(racesService.startRaces(raceRequest), HttpStatus.CREATED);
    }

    @PostMapping("/run-races/{idRace}")
    public ResponseEntity<RaceResultResponse> runRaces(@PathVariable Long idRace){
        return new ResponseEntity<>(racesService.runRace(idRace), HttpStatus.CREATED);
    }
}
