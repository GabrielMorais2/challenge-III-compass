package com.moraes.gabriel.mscars.model.payload;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PilotRequest {

    @NotBlank(message = "Pilot name must not be blank")
    @Pattern(regexp = "^(?!\\s)[\\p{L}\\d]+(?:[\\s-][\\p{L}\\d]+)*$", message = "name should only contain letters, numbers, and spaces")
    private String name;

    @NotNull(message = "Pilot age must not be null")
    @Min(value = 18, message = "Pilot must be at least 18 years old")
    private Integer age;
}
