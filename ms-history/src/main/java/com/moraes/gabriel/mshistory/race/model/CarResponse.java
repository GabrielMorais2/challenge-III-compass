package com.moraes.gabriel.mshistory.race.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "race_results")
public class CarResponse {

    private Long id;
    private String brand;
    private String model;
    private PilotResponse pilot;
    private String year;

}

