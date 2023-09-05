package com.moraes.gabriel.msraces.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.moraes.gabriel.msraces.model.payload.RaceResultResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(
        scope = RaceResultResponse.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String country;
    private LocalDate date;

    @OneToMany(mappedBy = "track",cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Race> race = new ArrayList<>();

}
