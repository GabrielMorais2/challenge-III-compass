package com.moraes.gabriel.msraces.domain.Race;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.moraes.gabriel.msraces.Client.CarResponse;
import com.moraes.gabriel.msraces.Client.CarsFeignClient;
import com.moraes.gabriel.msraces.domain.Track.Track;
import com.moraes.gabriel.msraces.domain.Track.TrackService;
import com.moraes.gabriel.msraces.rabbitmq.RabbitMQMessageProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

import static com.moraes.gabriel.msraces.config.AppConfig.NUM_LAPS;
import static com.moraes.gabriel.msraces.domain.Track.validations.Validations.validateNumCars;


@Service
@RequiredArgsConstructor
@Slf4j
public class RacesService {

    private final CarsFeignClient carsFeignClient;
    private final RabbitMQMessageProducer messageProducer;
    private final TrackService trackService;

    public List<CarResponse> getRandomCarsForRace(int numCars) {
        List<CarResponse> allCars = carsFeignClient.getAllCars();
        int maxCars = Math.min(numCars, allCars.size());

        List<CarResponse> selectedCars = new ArrayList<>(allCars);
        Collections.shuffle(selectedCars);

        return selectedCars.subList(0, maxCars);
    }

    private void setInitialCarPositions(List<CarResponse> cars) {
        for (int i = 0; i < cars.size(); i++) {
            cars.get(i).setPosition(i + 1);
        }
    }

    public void runRaces(RaceRequest request) {
        validateNumCars(request.getNumCars());

        Track track = trackService.getTrackById(request.getIdTrack());
        List<CarResponse> selectedCars = getRandomCarsForRace(request.getNumCars());
        setInitialCarPositions(selectedCars);

        Race race = new Race(request.getName(), selectedCars, track, new Date());

        simulateRace(race);

        RaceResultResponse raceResultResponse = new RaceResultResponse();
        raceResultResponse.setCars(race.getCars());
        raceResultResponse.setName(race.getName());
        raceResultResponse.setDateRace(race.getDate());

        try {
            messageProducer.publish(raceResultResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
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

                if (!overtaken && car.getSpeed() > nextCar.getSpeed() && (car.getPosition() < nextCar.getPosition())) {
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
            race.getCars().sort(Comparator.comparingInt(CarResponse::getPosition));
        }
    }
}
