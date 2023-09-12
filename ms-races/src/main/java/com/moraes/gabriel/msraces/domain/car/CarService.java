package com.moraes.gabriel.msraces.domain.car;

import com.moraes.gabriel.msraces.domain.car.payload.CarResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.moraes.gabriel.msraces.config.AppConfig.MAX_NUM_CARS;
import static com.moraes.gabriel.msraces.config.AppConfig.MIN_NUM_CARS;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarsFeignClient carsFeignClient;
    public List<CarResponse> getRandomCarsForRace() {
        int numCars = ThreadLocalRandom.current().nextInt(MIN_NUM_CARS, MAX_NUM_CARS + 1);

        List<CarResponse> cars = carsFeignClient.getRandomCars();
        numCars = Math.min(numCars, cars.size());

        return cars.subList(0, numCars);
    }
}
