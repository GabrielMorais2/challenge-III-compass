package com.moraes.gabriel.mscars.model.payload;

import com.moraes.gabriel.mscars.model.Pilot;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarRequest {

    @NotBlank(message = "brand must not be blank")
    @Pattern(regexp = "^(?!\\s)[\\p{L}\\d]+(?:[\\s-][\\p{L}\\d]+)*$", message = "brand should only contain letters, numbers, and spaces")
    private String brand;

    @NotBlank(message = "model must not be blank")
    @Pattern(regexp = "^(?!\\s)[\\p{L}\\d]+(?:[\\s-][\\p{L}\\d]+)*$", message = "model should only contain letters, numbers, and spaces")
    private String model;

    @Valid
    private PilotRequest pilot;

    @NotBlank(message = "year must not be blank")
    @Pattern(regexp = "^(19[789]\\d|20[012]\\d)$", message = "Please enter a valid year in YYYY format (eg 2015) and greater than 1970")
    private String year;
}
