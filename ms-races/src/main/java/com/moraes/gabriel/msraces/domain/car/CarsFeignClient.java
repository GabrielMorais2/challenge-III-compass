package com.moraes.gabriel.msraces.domain.car;

import com.moraes.gabriel.msraces.domain.car.payload.CarResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "MS-CARS")
public interface CarsFeignClient {

    @GetMapping("/v1/cars/limit?limit=10")
    List<CarResponse> getRandomCars();
}
