package com.moraes.gabriel.mshistory.domain.Race;

import com.moraes.gabriel.mshistory.Utils.JsonUtils;
import com.moraes.gabriel.mshistory.exception.ObjectNotFoundException;
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
class RaceHistoryServiceTest {
    @Mock
    private RaceHistoryRepository raceHistoryRepository;

    @InjectMocks
    private RaceHistoryService raceHistoryService;

    @Spy
    private ModelMapper mapper;

    private static final String RACE_RESULT_RESPONSE = "Payload/RACE_RESULT_RESPONSE.json";
    private static final String RACE_RESULT = "Payload/RACE_RESPONSE.json";

    @Test
    void saveRaceData() throws IOException {
        RaceResultResponse raceResultResponse = JsonUtils.getObjectFromFile(RACE_RESULT_RESPONSE, RaceResultResponse.class);

        raceHistoryService.saveRaceData(raceResultResponse);

        verify(raceHistoryRepository, times(1)).save(any());
    }

    @Test
    void getRaceDataById_WithExistingId_ReturnRaceResponse() throws IOException {
        RaceResult raceResult = JsonUtils.getObjectFromFile(RACE_RESULT, RaceResult.class);

        when(raceHistoryRepository.findById(anyString())).thenReturn(Optional.of(raceResult));

        RaceResponse raceResponse = raceHistoryService.getRaceDataById(anyString());

        assertNotNull(raceResponse);
        assertEquals(raceResult.getId(), raceResponse.getId());
        assertEquals(raceResult.getName(), raceResponse.getName());
        assertEquals(raceResult.getCars(), raceResponse.getCars());
    }

    @Test
    void getRaceDataById_WithNoExistingId_ReturnRaceResponse() {
        when(raceHistoryRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> raceHistoryService.getRaceDataById(anyString()));

        verify(raceHistoryRepository, times(1)).findById(anyString());
    }

    @Test
    void getAllRaceData() throws IOException {
        RaceResult raceResult = JsonUtils.getObjectFromFile(RACE_RESULT, RaceResult.class);

        when(raceHistoryRepository.findAll()).thenReturn(List.of(raceResult));

        List<RaceResponse> raceResponses = raceHistoryService.getAllRaceData();

        assertNotNull(raceResponses);
        assertEquals(1, raceResponses.size());
        assertEquals(raceResult.getName(), raceResponses.get(0).getName());
        assertEquals(raceResult.getCars(), raceResponses.get(0).getCars());
    }

}