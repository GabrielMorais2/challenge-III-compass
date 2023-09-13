package com.moraes.gabriel.msraces.domain.Race;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.moraes.gabriel.msraces.domain.Track.Track;
import com.moraes.gabriel.msraces.domain.car.payload.CarResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "race")
@Builder
public class Race {

    private String id;
    private String name;
    private List<CarResponse> cars;
    private Track track;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private RaceStatus status;

}
