package com.moraes.gabriel.mshistory.repository;

import com.moraes.gabriel.mshistory.race.model.RaceResultResponse;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends MongoRepository<RaceResultResponse, String> {
}
