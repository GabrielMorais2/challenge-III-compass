package com.moraes.gabriel.msraces.Repository;

import com.moraes.gabriel.msraces.model.Race;
import com.moraes.gabriel.msraces.model.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RaceRepository extends JpaRepository<Race, Long> {
}
