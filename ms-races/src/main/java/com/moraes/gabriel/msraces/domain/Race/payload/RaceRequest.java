package com.moraes.gabriel.msraces.domain.Race.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.moraes.gabriel.msraces.domain.Race.RaceStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RaceRequest {

    @NotBlank
    private String name;
    @NotNull
    private String idTrack;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    private RaceStatus status;
}
