package com.moraes.gabriel.mshistory.domain.Race;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.moraes.gabriel.mshistory.domain.Car.CarResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "race_results")
public class RaceResult {

    @Id
    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdAt;
    private RaceResultResponse resultResponse;

}
