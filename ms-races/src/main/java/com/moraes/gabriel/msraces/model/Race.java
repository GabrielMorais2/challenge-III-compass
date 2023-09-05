package com.moraes.gabriel.msraces.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(
        scope = Race.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
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
    private Track track;

}
