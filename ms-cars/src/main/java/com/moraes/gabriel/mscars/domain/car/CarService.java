package com.moraes.gabriel.mscars.domain.car;

import com.moraes.gabriel.mscars.domain.car.payload.CarRequest;
import com.moraes.gabriel.mscars.domain.car.payload.CarResponse;
import com.moraes.gabriel.mscars.exception.CarNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.moraes.gabriel.mscars.validations.validations.validateIdenticalCarsAndDrivers;


@Service
@AllArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final ModelMapper mapper;

    public CarResponse createCar(CarRequest carRequest) {
        Car car = mapper.map(carRequest, Car.class);
        validateIdenticalCarsAndDrivers(getAllCars(), carRequest);
        return mapper.map(carRepository.save(car), CarResponse.class);
    }

    public CarResponse getCarById(Long id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new CarNotFoundException("Car not found with id: " + id));
        return mapper.map(car, CarResponse.class);
    }

    public List<CarResponse> getAllCars() {
        List<Car> cars = carRepository.findAll();
        return cars.stream()
                .map(car -> mapper.map(car, CarResponse.class))
                .collect(Collectors.toList());

    }

    public void deleteCarById(Long id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new CarNotFoundException("Car not found with id: " + id));

        carRepository.delete(car);
    }

    public CarResponse updateCar(Long id, CarRequest carRequest) {
        Car existingCar = carRepository.findById(id)
                .orElseThrow(() -> new CarNotFoundException("Car not found with id: " + id));

        Car updatedCar = mapper.map(carRequest, Car.class);
        updatedCar.setId(existingCar.getId());

        validateIdenticalCarsAndDrivers(getAllCars(), carRequest);

        return mapper.map(carRepository.save(updatedCar), CarResponse.class);
    }

    public List<CarResponse> getRandomCars(int limit) {
        List<CarResponse> allCars = getAllCars();

        if (limit >= allCars.size()) {
            return allCars;
        } else {
            Collections.shuffle(allCars);
            return allCars.subList(0, limit);
        }
    }
}
