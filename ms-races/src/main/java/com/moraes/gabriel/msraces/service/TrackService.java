package com.moraes.gabriel.msraces.service;

import com.moraes.gabriel.msraces.Repository.TrackRepository;
import com.moraes.gabriel.msraces.model.Track;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrackService {

    private final TrackRepository trackRepository;
    public Track getTrackById(Long idTrack) {
        return trackRepository.findById(idTrack)
                .orElseThrow(() -> new EntityNotFoundException("Track not found with id: " + idTrack));
    }
}
