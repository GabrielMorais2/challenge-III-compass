package com.moraes.gabriel.msraces.service;

import com.moraes.gabriel.msraces.cars.CarResponse;
import com.moraes.gabriel.msraces.feign.CarsFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class RacesService {

    private final CarsFeignClient carsFeignClient;

    public List<CarResponse> getRandomCarsForRace() {
        List<CarResponse> allCars = carsFeignClient.getAllCars();

        int maxCars = Math.min(10, allCars.size());
        Random random = new Random();

        List<CarResponse> selectedCars = new ArrayList<>();

        while (selectedCars.size() < maxCars) {
            int randomIndex = random.nextInt(allCars.size());
            selectedCars.add(allCars.get(randomIndex));
            allCars.remove(randomIndex);
        }

        return selectedCars;
    }

}
