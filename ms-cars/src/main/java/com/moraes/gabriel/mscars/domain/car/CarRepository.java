package com.moraes.gabriel.mscars.domain.car;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

}
