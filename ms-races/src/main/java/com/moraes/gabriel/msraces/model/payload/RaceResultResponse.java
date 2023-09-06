package com.moraes.gabriel.msraces.model.payload;

import com.moraes.gabriel.msraces.cars.CarResponse;
import com.moraes.gabriel.msraces.model.Track;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RaceResultResponse {

    private Long id;
    private String name;
    private List<CarResponse> cars = new ArrayList<>();
    private Track track;
}
