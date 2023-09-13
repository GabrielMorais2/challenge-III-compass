package com.moraes.gabriel.mshistory.domain.Car;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "race_results")
public class CarResponse {

    private Long id;
    private String brand;
    private String model;
    private PilotResponse pilot;

    @JsonFormat(pattern = "yyyy")
    private Date year;

    private int position;
}

