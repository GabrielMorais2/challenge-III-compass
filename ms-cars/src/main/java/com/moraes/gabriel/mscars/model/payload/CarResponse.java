package com.moraes.gabriel.mscars.model.payload;

import com.moraes.gabriel.mscars.model.Pilot;
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
    private Pilot pilot;
    private String year;
}

