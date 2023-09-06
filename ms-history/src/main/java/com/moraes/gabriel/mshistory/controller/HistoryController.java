package com.moraes.gabriel.mshistory.controller;

import com.moraes.gabriel.mshistory.race.model.RaceResultResponse;
import com.moraes.gabriel.mshistory.service.HistoryService;
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
public class HistoryController {
    private final HistoryService historyService;

    @GetMapping("/race-data")
    public ResponseEntity<List<RaceResultResponse>> getAllRaceData() {
        return  new ResponseEntity<>(historyService.getAllRaceData(), HttpStatus.OK);

    }

    @GetMapping("/race-data/{id}")
    public ResponseEntity<RaceResultResponse> getRaceDataById(@PathVariable String id) {
        return new ResponseEntity<>(historyService.getRaceDataById(id), HttpStatus.OK);
    }

}
