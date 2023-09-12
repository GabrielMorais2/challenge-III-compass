package com.moraes.gabriel.msraces.domain.car.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PilotResponse {

    private String name;
    private Integer age;

}
