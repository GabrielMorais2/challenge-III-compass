package com.moraes.gabriel.msraces.domain.Race.validations;

import com.moraes.gabriel.msraces.domain.Race.Race;
import com.moraes.gabriel.msraces.domain.Race.RaceStatus;
import com.moraes.gabriel.msraces.exception.InvalidStatusClassException;

public interface RaceValidations {
    static void validateStatusRunRace(Race race){
        if (race.getStatus() != RaceStatus.CREATED) {
            throw new InvalidStatusClassException("It is only possible to run a race with CREATED status");
        }
    }

}
