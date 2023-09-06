package com.moraes.gabriel.mshistory.domain.Race;

import com.moraes.gabriel.mshistory.domain.Car.CarResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RaceResponse {

    private String id;
    private String name;
    private LocalDateTime createdAt;
    private List<CarResponse> cars;

}
