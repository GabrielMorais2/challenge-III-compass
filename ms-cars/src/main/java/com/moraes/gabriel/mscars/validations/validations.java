package com.moraes.gabriel.mscars.validations;

import com.moraes.gabriel.mscars.domain.car.payload.CarRequest;
import com.moraes.gabriel.mscars.domain.car.payload.CarResponse;
import com.moraes.gabriel.mscars.exception.CarAlreadyExistsException;
import com.moraes.gabriel.mscars.exception.PilotAlreadyExistsException;

import java.util.List;

public interface validations {

    static void validateIdenticalCarsAndDrivers(List<CarResponse> cars, CarRequest carRequest){
        for (CarResponse car : cars) {
            if(car.getYear().equals(carRequest.getYear())
                && car.getModel().equals(carRequest.getModel())
                && car.getBrand().equals(carRequest.getBrand())){
                throw new CarAlreadyExistsException("There is already a corresponding car registered.");
            }

            if(car.getPilot().getName().equals(carRequest.getPilot().getName())
                && car.getPilot().getAge().equals(carRequest.getPilot().getAge())){
                throw new PilotAlreadyExistsException("There is already a corresponding Pilot registered.");
            }
        }
    }
}
