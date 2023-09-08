package com.moraes.gabriel.msraces.domain.Track.validations;

import static com.moraes.gabriel.msraces.config.AppConfig.MAX_NUM_CARS;
import static com.moraes.gabriel.msraces.config.AppConfig.MIN_NUM_CARS;

public interface Validations {

    static void validateNumCars(int numCars) {
        if (numCars < MIN_NUM_CARS || numCars > MAX_NUM_CARS) {
            throw new IllegalArgumentException("The number of cars must be between " + MIN_NUM_CARS + " and " + MAX_NUM_CARS);
        }
    }
}
