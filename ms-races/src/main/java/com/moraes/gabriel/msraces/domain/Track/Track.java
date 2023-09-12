package com.moraes.gabriel.msraces.domain.Track;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor

@Document(collection = "track")
public class Track {

    @Id
    private String id;
    private String name;
    private String country;

}
