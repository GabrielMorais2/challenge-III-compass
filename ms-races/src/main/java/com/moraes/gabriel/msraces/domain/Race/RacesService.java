package com.moraes.gabriel.msraces.domain.Race;

import com.moraes.gabriel.msraces.domain.Race.payload.RaceRequest;
import com.moraes.gabriel.msraces.domain.Race.payload.RaceResponse;
import com.moraes.gabriel.msraces.domain.Track.Track;
import com.moraes.gabriel.msraces.domain.Track.TrackService;
import com.moraes.gabriel.msraces.domain.car.CarService;
import com.moraes.gabriel.msraces.domain.car.payload.CarResponse;
import com.moraes.gabriel.msraces.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.moraes.gabriel.msraces.domain.Race.validations.RaceValidations.validateStatusRunRace;


@Service
@RequiredArgsConstructor
@Slf4j
public class RacesService {

    private final CarService carService;
    private final TrackService trackService;
    private final RaceSimulationService raceSimulationService;
    private final RaceResultPublisher raceResultPublisher;
    private final RaceRepository raceRepository;
    private final ModelMapper mapper;


    public RaceResponse getRaceById(String id) {
        Race race = raceRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Race not found with id: " + id));
        return mapper.map(race, RaceResponse.class);
    }


    public RaceResponse createRaces(RaceRequest raceRequest) {
        Track track = trackService.getTrackById(raceRequest.getIdTrack());
        List<CarResponse> randomCars = carService.getRandomCarsForRace();

        Race race = Race.builder()
                .track(track)
                .cars(randomCars)
                .name(track.getName())
                .date(raceRequest.getDate())
                .status(RaceStatus.CREATED)
                .build();

        return mapper.map(raceRepository.save(race), RaceResponse.class);
    }


    public void runRaces(String id) {
        Race race =  mapper.map(getRaceById(id), Race.class);
        validateStatusRunRace(race);
        raceSimulationService.runRaces(race);

        race.setStatus(RaceStatus.FINISHED);
        raceResultPublisher.publishRaceResult(race);

        raceRepository.save(race);
    }

    public List<RaceResponse> getAllRaces() {
        List<Race> races = raceRepository.findAll();
        return races.stream()
                .map(race -> mapper.map(race, RaceResponse.class))
                .collect(Collectors.toList());

    }
}
