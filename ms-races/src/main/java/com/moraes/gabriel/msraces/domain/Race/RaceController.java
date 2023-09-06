package com.moraes.gabriel.msraces.domain.Race;

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
@RequestMapping("/api/v1/races")
public class RaceController {

    private final RacesService racesService;

    @PostMapping("/start-race")
    public ResponseEntity<String> startRaces(@Valid @RequestBody RaceRequest raceRequest){
        racesService.startRaces(raceRequest);
        return new ResponseEntity<>("The race has started successfully.", HttpStatus.CREATED);
    }

}
