package com.moraes.gabriel.msraces.Client;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private int position;

    @JsonIgnore
    private int speed = 0;

    public void increaseSpeed() {
        this.speed = 100 + (int) (Math.random() * 101);
    }
}

