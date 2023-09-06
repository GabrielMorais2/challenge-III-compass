package com.moraes.gabriel.mshistory.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moraes.gabriel.mshistory.exception.ObjectNotFoundException;
import com.moraes.gabriel.mshistory.race.model.CarResponse;
import com.moraes.gabriel.mshistory.race.model.RaceResultResponse;
import com.moraes.gabriel.mshistory.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final HistoryRepository historyRepository;
    private final ModelMapper mapper;

    public void saveRaceData(List<CarResponse> carResponses) {
        LocalDateTime currentDateTime = LocalDateTime.now();

        RaceResultResponse raceResultResponse = new RaceResultResponse();
        raceResultResponse.setCars(carResponses);
        raceResultResponse.setCreatedAt(currentDateTime);

        historyRepository.save(raceResultResponse);
    }

    public RaceResultResponse getRaceDataById(String id) {
        return historyRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Race not found with id: " + id ));
    }

    public List<RaceResultResponse> getAllRaceData() {
        List<RaceResultResponse> raceDataList = historyRepository.findAll();
        return raceDataList.stream()
                .map(allRaceData -> mapper.map(allRaceData, RaceResultResponse.class))
                .collect(Collectors.toList());
    }
}
