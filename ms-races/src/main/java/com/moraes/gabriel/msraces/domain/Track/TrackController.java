package com.moraes.gabriel.msraces.domain.Track;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/track")
@RequiredArgsConstructor
public class TrackController {

    private final TrackService trackService;
    @PostMapping("/create-track")
    public ResponseEntity<TrackResponse> createCar(@Valid @RequestBody TrackRequest trackRequest){
        return new ResponseEntity<>(trackService.createTrack(trackRequest), HttpStatus.CREATED);
    }

}
