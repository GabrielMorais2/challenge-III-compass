package com.moraes.gabriel.mshistory.race.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "race_results")
public class RaceResultResponse {

    @Id
    private String id;
    private List<CarResponse> cars;
    private LocalDateTime createdAt;
}
