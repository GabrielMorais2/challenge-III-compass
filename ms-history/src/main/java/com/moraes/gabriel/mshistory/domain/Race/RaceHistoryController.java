package com.moraes.gabriel.mshistory.domain.Race;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/history")
@RequiredArgsConstructor
public class RaceHistoryController {
    private final RaceHistoryService raceHistoryService;

    @GetMapping("/race-data")
    public ResponseEntity<List<RaceResponse>> getAllRaceData() {
        return  new ResponseEntity<>(raceHistoryService.getAllRaceData(), HttpStatus.OK);

    }

    @GetMapping("/race-data/{id}")
    public ResponseEntity<RaceResponse> getRaceDataById(@PathVariable String id) {
        return new ResponseEntity<>(raceHistoryService.getRaceDataById(id), HttpStatus.OK);
    }

}
