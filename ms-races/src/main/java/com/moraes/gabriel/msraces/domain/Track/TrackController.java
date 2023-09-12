package com.moraes.gabriel.msraces.domain.Track;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    @Operation(
            summary = "Get a Track by ID",
            description = "Retrieve details of a specific track based on its ID."
    )@GetMapping("/{id}")
    public ResponseEntity<TrackResponse> getCarById(@PathVariable String id) {
        return new ResponseEntity<>(trackService.getTrackResponseById(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Get All Tracks",
            description = "Retrieve a list of all available tracks."
    )@GetMapping
    public ResponseEntity<List<TrackResponse>> getAllCars() {
        return new ResponseEntity<>(trackService.getAllTracks(), HttpStatus.OK);
    }


}
