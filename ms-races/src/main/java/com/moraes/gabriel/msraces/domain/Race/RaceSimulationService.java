package com.moraes.gabriel.msraces.domain.Race;

import com.moraes.gabriel.msraces.domain.car.payload.CarResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

import static com.moraes.gabriel.msraces.config.AppConfig.NUM_LAPS;

@Service
@RequiredArgsConstructor
@Slf4j
public class RaceSimulationService {

    public void runRaces(Race race) {
        setInitialCarPositions(race.getCars());
        simulateRace(race);
    }

    private void setInitialCarPositions(List<CarResponse> cars) {
        for (int i = 0; i < cars.size(); i++) {
            cars.get(i).setPosition(i + 1);
        }
    }

    private void simulateRace(Race race) {
        for (int lap = 0; lap < NUM_LAPS; lap++) {
            for (CarResponse car : race.getCars()) {
                log.info("Piloto: " + car.getPilot().getName() +
                        " Posição atual: " + car.getPosition() +
                        " Velocidade: " + car.getSpeed());
                car.increaseSpeed();
            }

            boolean overtaken = false;

            for (int i = 0; i < race.getCars().size() - 1; i++) {
                CarResponse car = race.getCars().get(i);
                CarResponse nextCar = race.getCars().get(i + 1);

                if (!overtaken && car.getSpeed() > nextCar.getSpeed() && (car.getPosition() > nextCar.getPosition())) {
                    log.info("Troca de posição: " + car.getPilot().getName() +
                            " de " + car.getPosition() + " para " + nextCar.getPosition());
                    int tempPosition = car.getPosition();
                    car.setPosition(nextCar.getPosition());
                    nextCar.setPosition(tempPosition);
                    overtaken = true;
                } else {
                    overtaken = false;
                }
            }
            race.getCars().sort(Comparator.comparingInt(CarResponse::getPosition).reversed());
        }
    }
}
