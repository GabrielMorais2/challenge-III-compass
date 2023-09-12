package com.moraes.gabriel.msraces.domain.Race;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.moraes.gabriel.msraces.Client.CarResponse;
import com.moraes.gabriel.msraces.domain.Track.Track;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Race {

    private String name;
    private List<CarResponse> cars;
    private Track track;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

}
