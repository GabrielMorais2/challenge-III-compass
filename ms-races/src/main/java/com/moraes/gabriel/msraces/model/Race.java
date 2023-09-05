package com.moraes.gabriel.msraces.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.moraes.gabriel.msraces.cars.CarResponse;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Race {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private LocalDateTime startTime;

    @ElementCollection
    private List<Long> carsIds;

    @ManyToOne
    @JoinColumn(name = "track_id")
    @JsonManagedReference
    private Track track;

}
