package com.moraes.gabriel.msraces.domain.car.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarResponse {

    private Long id;
    private String brand;
    private String model;
    private PilotResponse pilot;

    @JsonFormat(pattern = "yyyy")
    private Date year;

    private int position;

    @JsonIgnore
    private int speed = 0;

    public void increaseSpeed() {
        this.speed = 100 + (int) (Math.random() * 101);
    }
}

