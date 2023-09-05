package com.moraes.gabriel.msraces.service;

import com.moraes.gabriel.msraces.Repository.RaceRepository;
import com.moraes.gabriel.msraces.Repository.TrackRepository;
import com.moraes.gabriel.msraces.cars.CarResponse;
import com.moraes.gabriel.msraces.cars.client.CarsFeignClient;
import com.moraes.gabriel.msraces.model.Race;
import com.moraes.gabriel.msraces.model.Track;
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
    private final TrackRepository trackRepository;
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

    public RaceResultResponse startRaces(Long idTrack) {
        Track track = trackRepository.findById(idTrack)
                .orElseThrow(() -> new EntityNotFoundException("Track not found with id: " + idTrack));

        List<CarResponse> selectedCars = getRandomCarsForRace();

        Race race = createRace("teste", track, selectedCars);

        System.out.println(race.getCarsIds());

        race = raceRepository.save(race);

        runRace(race);

        return mapper.map(race, RaceResultResponse.class);
    }

    public void runRace(Race race) {
        List<Long> carsInRace = race.getCarsIds();
        for (int lap = 1; lap <= numberOfLaps; lap++) {
            for (int i = 1; i < carsInRace.size(); i++) {
                Long carAhead = carsInRace.get(i - 1);
                Long car = carsInRace.get(i);
                if (canOvertake()) {
                    swapCars(carsInRace, i, i - 1);
                }
            }
        }
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
