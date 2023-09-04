package com.moraes.gabriel.mscars.repository;

import com.moraes.gabriel.mscars.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    boolean existsByBrandAndModelAndYear(String brand, String model, String year);
    boolean existsByPilotNameAndPilotAge(String name, Integer age);
}
