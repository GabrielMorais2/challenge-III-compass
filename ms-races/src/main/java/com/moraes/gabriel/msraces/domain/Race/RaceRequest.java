package com.moraes.gabriel.msraces.domain.Race;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RaceRequest {

    @NotBlank
    private String name;
    @NotNull
    private String idTrack;
    @NotNull
    private int numCars;

    @NotNull
    private int laps;
}
