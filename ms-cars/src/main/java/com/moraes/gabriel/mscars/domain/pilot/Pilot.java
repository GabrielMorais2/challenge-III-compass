package com.moraes.gabriel.mscars.domain.pilot;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pilot {

    private String name;
    private Integer age;
}
