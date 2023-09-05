package com.moraes.gabriel.msraces.model.payload;

import com.moraes.gabriel.msraces.model.Track;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RaceResponse {

    private Long id;
    private String name;
    private LocalDateTime startTime;
    private List<Long> carsIds;
    private Track track;

}
