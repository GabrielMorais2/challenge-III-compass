package com.moraes.gabriel.msraces.domain.Race;

import com.moraes.gabriel.msraces.Client.CarResponse;
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
    private List<CarResponse> cars;

}
