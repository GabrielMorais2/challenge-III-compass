package com.moraes.gabriel.msraces.Client.client;

import com.moraes.gabriel.msraces.Client.CarResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "ms-cars", url="localhost:8080/api/v1")
public interface CarsFeignClient {

    @GetMapping("/cars")
    List<CarResponse> getAllCars();
    @GetMapping("/cars/{id}")
    CarResponse getCarById(@PathVariable Long id);
}
