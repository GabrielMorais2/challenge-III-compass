package com.moraes.gabriel.msraces.cars.client;

import com.moraes.gabriel.msraces.cars.CarResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "ms-cars", url="localhost:8080")
public interface CarsFeignClient {

    @GetMapping("/api/v1/cars")
    List<CarResponse> getAllCars();
}
