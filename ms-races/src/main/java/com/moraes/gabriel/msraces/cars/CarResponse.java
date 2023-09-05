package com.moraes.gabriel.msraces.cars;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarResponse {

    private Long id;
    private String brand;
    private String model;
    private PilotResponse pilot;
    private String year;
}

