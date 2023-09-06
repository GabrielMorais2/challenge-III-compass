package com.moraes.gabriel.msraces.domain.Track;

import com.moraes.gabriel.msraces.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrackService {

    private final TrackRepository trackRepository;
    private final ModelMapper mapper;
    public Track getTrackById(String idTrack) {
        return trackRepository.findById(idTrack)
                .orElseThrow(() -> new ObjectNotFoundException("Track not found with id: " + idTrack));
    }

    public TrackResponse createTrack(TrackRequest trackRequest) {
        Track car = mapper .map(trackRequest, Track.class);
        return mapper.map(trackRepository.save(car), TrackResponse.class);

    }
}
