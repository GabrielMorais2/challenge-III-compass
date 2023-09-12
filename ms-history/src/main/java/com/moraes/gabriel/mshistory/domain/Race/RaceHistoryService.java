package com.moraes.gabriel.mshistory.domain.Race;

import com.moraes.gabriel.mshistory.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RaceHistoryService {

    private final RaceHistoryRepository raceHistoryRepository;
    private final ModelMapper mapper;

    public void saveRaceData(RaceResultResponse raceResultResponse) {
        RaceResult raceResult = new RaceResult();
        raceResult.setRaceResult(raceResultResponse);
        raceResult.setCreatedAt(new Date());

        raceHistoryRepository.save(raceResult);
    }

    public RaceResponse getRaceDataById(String id) {
        RaceResult raceResult = raceHistoryRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("RaceResult not found with id: " + id ));

        return mapper.map(raceResult, RaceResponse.class);

    }

    public List<RaceResponse> getAllRaceData() {
        List<RaceResult> raceDataList = raceHistoryRepository.findAll();
        return raceDataList.stream()
                .map(allRaceData -> mapper.map(allRaceData, RaceResponse.class))
                .collect(Collectors.toList());
    }
}
