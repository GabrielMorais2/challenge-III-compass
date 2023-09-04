package com.moraes.gabriel.mscars.model.payload;

import com.moraes.gabriel.mscars.model.Pilot;
import jakarta.validation.constraints.Pattern;
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
    private Pilot pilot;

    @Pattern(regexp = "^(19[789]\\d|20[012]\\d)$", message = "Please enter a valid year in YYYY format (eg 2015) and greater than 1970")
    private String year;
}

