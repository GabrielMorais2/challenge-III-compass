package com.moraes.gabriel.msraces.domain.Race.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.moraes.gabriel.msraces.domain.Race.RaceStatus;
import com.moraes.gabriel.msraces.domain.Track.Track;
import com.moraes.gabriel.msraces.domain.car.payload.CarResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RaceResponse {

    private String id;
    private String name;
    private RaceStatus status;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private Track track;
    private List<CarResponse> cars;


}
