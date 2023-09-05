package com.moraes.gabriel.msraces.service;

import com.moraes.gabriel.msraces.Repository.RaceRepository;
import com.moraes.gabriel.msraces.cars.CarResponse;
import com.moraes.gabriel.msraces.cars.client.CarsFeignClient;
import com.moraes.gabriel.msraces.model.Race;
import com.moraes.gabriel.msraces.model.Track;
import com.moraes.gabriel.msraces.model.payload.RaceRequest;
import com.moraes.gabriel.msraces.model.payload.RaceResponse;
import com.moraes.gabriel.msraces.model.payload.RaceResultResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RacesService {

    private final CarsFeignClient carsFeignClient;
    private final TrackService trackService;
    private final RaceRepository raceRepository;
    private final ModelMapper mapper;
    private final int numberOfLaps = 10;

    public List<CarResponse> getRandomCarsForRace() {
        List<CarResponse> allCars = carsFeignClient.getAllCars();
        int maxCars = Math.min(10, allCars.size());
        List<CarResponse> selectedCars = new ArrayList<>(allCars);
        Collections.shuffle(selectedCars);
        return selectedCars.subList(0, maxCars);
    }

    public RaceResponse startRaces(RaceRequest request) {
        Track track = trackService.getTrackById(request.getIdTrack());
        List<CarResponse> selectedCars = getRandomCarsForRace();
        Race race = createRace(request.getName(), track, selectedCars);
        race = raceRepository.save(race);
        return mapper.map(race, RaceResponse.class);
    }

    public RaceResultResponse runRace(Long idRace) {
        Race race = getRaceById(idRace);
        List<CarResponse> carsDetails = new ArrayList<>();

        for (int lap = 1; lap <= numberOfLaps; lap++) {
            for (int i = 1; i < race.getCarsIds().size(); i++) {
                if (canOvertake()) {
                    swapCars(race.getCarsIds(), i, i - 1);
                }
            }
        }

        race.getCarsIds().forEach(carId -> {
            CarResponse carResponse = carsFeignClient.getCarById(carId);
            carsDetails.add(carResponse);
        });

        RaceResultResponse raceResultResponse = mapper.map(race, RaceResultResponse.class);
        raceResultResponse.setCars(carsDetails);

        return raceResultResponse;
    }

    public Race getRaceById(Long idRace) {
        return raceRepository.findById(idRace)
                .orElseThrow(() -> new EntityNotFoundException("Race not found with id: " + idRace));
    }

    private Race createRace(String name, Track track, List<CarResponse> selectedCars) {
        Race race = new Race();
        race.setName(name);
        race.setStartTime(LocalDateTime.now());
        race.setCarsIds(selectedCars.stream().map(CarResponse::getId).collect(Collectors.toList()));
        race.setTrack(track);
        return race;
    }

    private boolean canOvertake() {
        double probabilityOfOvertake = 0.3;
        double randomValue = Math.random();
        return randomValue < probabilityOfOvertake;
    }

    private void swapCars(List<Long> carsInRace, int index1, int index2) {
        Long temp = carsInRace.get(index1);
        carsInRace.set(index1, carsInRace.get(index2));
        carsInRace.set(index2, temp);
    }
}
