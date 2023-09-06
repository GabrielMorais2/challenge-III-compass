package com.moraes.gabriel.msraces.Client;

import com.moraes.gabriel.msraces.Client.CarResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "MS-CARS")
public interface CarsFeignClient {

    @GetMapping("/api/v1/cars")
    List<CarResponse> getAllCars();
}
