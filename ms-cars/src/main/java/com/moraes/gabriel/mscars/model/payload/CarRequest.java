package com.moraes.gabriel.mscars.model.payload;

import com.moraes.gabriel.mscars.model.Pilot;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarRequest {

    @NotBlank
    private String brand;

    @NotBlank
    private String model;

    @NotNull
    private Pilot pilot;

    @NotNull
    private String year;
}
