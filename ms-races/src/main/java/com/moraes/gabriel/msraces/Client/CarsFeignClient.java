package com.moraes.gabriel.msraces.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "MS-CARS")
public interface CarsFeignClient {

    @GetMapping("/v1/cars")
    List<CarResponse> getAllCars();
}
