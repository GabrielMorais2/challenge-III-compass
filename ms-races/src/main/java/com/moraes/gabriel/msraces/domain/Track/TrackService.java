package com.moraes.gabriel.msraces.domain.Track;

import com.moraes.gabriel.msraces.domain.Track.payload.TrackRequest;
import com.moraes.gabriel.msraces.domain.Track.payload.TrackResponse;
import com.moraes.gabriel.msraces.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrackService {

    private final TrackRepository trackRepository;
    private final ModelMapper mapper;
    public Track getTrackById(String idTrack) {
        return trackRepository.findById(idTrack)
                .orElseThrow(() -> new ObjectNotFoundException("Track not found with id: " + idTrack));
    }

    public TrackResponse getTrackResponseById(String idTrack) {
        Track track = trackRepository.findById(idTrack)
                .orElseThrow(() -> new ObjectNotFoundException("Track not found with id: " + idTrack));
        return mapper.map(track, TrackResponse.class);
    }

    public TrackResponse createTrack(TrackRequest trackRequest) {
        Track car = mapper .map(trackRequest, Track.class);
        return mapper.map(trackRepository.save(car), TrackResponse.class);

    }

    public List<TrackResponse> getAllTracks() {
        List<Track> tracks = trackRepository.findAll();
        return tracks.stream()
                .map(track -> mapper.map(track, TrackResponse.class))
                .collect(Collectors.toList());
    }

}
