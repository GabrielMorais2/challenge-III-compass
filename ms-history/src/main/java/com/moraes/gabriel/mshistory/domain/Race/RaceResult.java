package com.moraes.gabriel.mshistory.domain.Race;

import com.moraes.gabriel.mshistory.domain.Car.CarResponse;
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
public class RaceResult {

    @Id
    private String id;
    private String name;
    private List<CarResponse> cars;
    private LocalDateTime createdAt;
}
