package com.moraes.gabriel.msraces.domain.Track;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrackResponse {

    private String id;
    private String name;
    private String country;
    private LocalDate date;

}
