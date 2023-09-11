package com.moraes.gabriel.mscars.model.payload;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.moraes.gabriel.mscars.model.Pilot;
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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy")
    private Date year;
}

