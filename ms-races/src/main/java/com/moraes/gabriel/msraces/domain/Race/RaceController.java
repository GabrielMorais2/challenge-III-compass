package com.moraes.gabriel.msraces.domain.Race;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/races")
public class RaceController {

    private final RacesService racesService;


    @Operation(
            summary = "Start a Race",
            description = "Initiate a new race with the provided race request data. The race will be carried out automatically."
    )
    @PostMapping("/start")
    public ResponseEntity<String> startRaces(@Valid @RequestBody RaceRequest raceRequest){
        racesService.runRaces(raceRequest);
        return new ResponseEntity<>("Race carried out successfully", HttpStatus.CREATED);
    }

}
