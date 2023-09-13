package com.moraes.gabriel.msraces.domain.Race.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.moraes.gabriel.msraces.domain.Race.RaceStatus;
import com.moraes.gabriel.msraces.domain.Track.Track;
import com.moraes.gabriel.msraces.domain.car.payload.CarResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RaceResultResponse {

    private String name;
    private List<CarResponse> cars;
    private Track track;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateRace;
    private RaceStatus status;

}

