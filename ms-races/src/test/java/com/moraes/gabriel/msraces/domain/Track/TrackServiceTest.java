package com.moraes.gabriel.msraces.domain.Track;

import Utils.JsonUtils;
import com.moraes.gabriel.msraces.domain.Track.payload.TrackRequest;
import com.moraes.gabriel.msraces.domain.Track.payload.TrackResponse;
import com.moraes.gabriel.msraces.exception.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrackServiceTest {

    @Mock
    private TrackRepository trackRepository;

    @InjectMocks
    private TrackService trackService;

    @Spy
    private ModelMapper mapper;

    private static final String TRACK = "/Payload/track/TRACK.json";
    private static final String TRACK_REQUEST = "/Payload/track/TRACK_REQUEST.json";
    private static final String TRACK_RESPONSE = "/Payload/track/TRACK.json";

    @Test
    void getTrackById_WithExistingTrack_ReturnAnTrack() throws IOException {
        Track track = JsonUtils.getObjectFromFile(TRACK, Track.class);

        when(trackRepository.findById(anyString())).thenReturn(Optional.of(track));

        Track response = trackService.getTrackById(anyString());

        assertNotNull(response);
        assertEquals(track.getId(), response.getId());
        assertEquals(track.getName(), response.getName());
        assertEquals(track.getCountry(), response.getCountry());
    }

    @Test
    void getTrackResponseById_WithExistingTrack_ReturnAnTrack() throws IOException {
        Track track = JsonUtils.getObjectFromFile(TRACK, Track.class);

        when(trackRepository.findById(anyString())).thenReturn(Optional.of(track));

        TrackResponse response = trackService.getTrackResponseById(anyString());

        assertNotNull(response);
        assertEquals(track.getId(), response.getId());
        assertEquals(track.getName(), response.getName());
        assertEquals(track.getCountry(), response.getCountry());
    }
    @Test
    void getTrackById_WithNoExistingTrack_ReturnAnObjectNotFoundException() {
        when(trackRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> trackService.getTrackById(anyString()));

        verify(trackRepository, times(1)).findById(anyString());
    }

    @Test
    void createTrack_ReturnAnTrackResponse() throws IOException {
        TrackResponse trackResponse = JsonUtils.getObjectFromFile(TRACK_RESPONSE, TrackResponse.class);
        TrackRequest trackRequest = JsonUtils.getObjectFromFile(TRACK_REQUEST, TrackRequest.class);
        Track track = JsonUtils.getObjectFromFile(TRACK, Track.class);

        when(trackRepository.save(any())).thenReturn(track);

        TrackResponse response = trackService.createTrack(trackRequest);

        assertNotNull(response);
        assertEquals(trackResponse.getId(), response.getId());
        assertEquals(trackResponse.getName(), response.getName());
        assertEquals(trackResponse.getCountry(), response.getCountry());
    }


    @Test
    void getAllTracks_ReturnAnListOfTracksResponse() throws IOException {
        Track track = JsonUtils.getObjectFromFile(TRACK, Track.class);

        when(trackRepository.findAll()).thenReturn(List.of(track));

        List<TrackResponse> response = trackService.getAllTracks();

        assertNotNull(response);
        assertEquals(track.getId(), response.get(0).getId());
        assertEquals(track.getName(), response.get(0).getName());
    }

}