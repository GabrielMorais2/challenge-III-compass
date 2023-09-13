package com.moraes.gabriel.msraces.domain.Race;

import com.moraes.gabriel.msraces.domain.Race.payload.RaceRequest;
import com.moraes.gabriel.msraces.domain.Race.payload.RaceResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/races")
public class RaceController {

    private final RacesService racesService;

    @PostMapping("/create")
    @Operation(
            summary = "Create a Race",
            description = "Create a new race with the data provided in the race request. The race will be carried out automatically."
    )
    public ResponseEntity<RaceResponse> createRace(@Valid @RequestBody RaceRequest raceRequest) {
        return new ResponseEntity<>(racesService.createRaces(raceRequest), HttpStatus.CREATED);
    }

    @PostMapping("/run/{id}")
    @Operation(
            summary = "Start a Race",
            description = "Initiate a new race with the data provided in the race id. The race will be carried out automatically."
    )
    public ResponseEntity<RaceResponse> startRaces(@PathVariable String id) {
        return new ResponseEntity<>(racesService.runRaces(id), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get a Race by ID",
            description = "Retrieve details of a specific race based on its ID."
    )
    public ResponseEntity<RaceResponse> getCarById(@PathVariable String id) {
        return new ResponseEntity<>(racesService.getRaceById(id), HttpStatus.OK);
    }

    @GetMapping
    @Operation(
            summary = "Get All Races",
            description = "Retrieve a list of all available races."
    )
    public ResponseEntity<List<RaceResponse>> getAllCars() {
        return new ResponseEntity<>(racesService.getAllRaces(), HttpStatus.OK);
    }

}
