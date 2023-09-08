package com.moraes.gabriel.msraces.domain.Track;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/tracks")
@RequiredArgsConstructor
public class TrackController {

    private final TrackService trackService;

    @Operation(
            summary = "Create a New Track",
            description = "Create a new track with the provided track request data. The track details will be saved for future reference."
    )
    @PostMapping
    public ResponseEntity<TrackResponse> createTrack(@Valid @RequestBody TrackRequest trackRequest){
        return new ResponseEntity<>(trackService.createTrack(trackRequest), HttpStatus.CREATED);
    }

}
