package com.moraes.gabriel.msraces.domain.Race;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.moraes.gabriel.msraces.Client.CarResponse;
import com.moraes.gabriel.msraces.domain.Track.Track;
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
    private Track track;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateRace;

}

