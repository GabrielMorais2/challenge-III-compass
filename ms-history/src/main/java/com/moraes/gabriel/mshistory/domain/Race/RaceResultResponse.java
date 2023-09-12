package com.moraes.gabriel.mshistory.domain.Race;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.moraes.gabriel.mshistory.domain.Car.CarResponse;
import com.moraes.gabriel.mshistory.domain.Track.TrackResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RaceResultResponse {

    private String name;

    private List<CarResponse> cars;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateRace;

    private TrackResponse track;
}
