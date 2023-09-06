package com.moraes.gabriel.mshistory.domain.Race;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.moraes.gabriel.mshistory.domain.Car.CarResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RaceResultResponse {

    private String name;

    @JsonProperty("cars")
    private List<CarResponse> cars;

}
