package com.moraes.gabriel.msraces.Repository;

import com.moraes.gabriel.msraces.model.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {
}
