package com.moraes.gabriel.mscars.domain.car;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.moraes.gabriel.mscars.domain.pilot.Pilot;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand;
    private String model;

    @Embedded
    private Pilot pilot;

    @JsonFormat(pattern = "yyyy")
    private Date year;
}
