package com.moraes.gabriel.mshistory.domain.Race;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RaceResponse {

    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdAt;

    private RaceResultResponse raceResult;
}
