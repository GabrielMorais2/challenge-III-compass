package com.moraes.gabriel.msraces.domain.Race;

import com.moraes.gabriel.msraces.Client.CarResponse;
import com.moraes.gabriel.msraces.domain.Track.Track;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Race {

    private String name;
    private List<CarResponse> cars;
    private Track track;

}
