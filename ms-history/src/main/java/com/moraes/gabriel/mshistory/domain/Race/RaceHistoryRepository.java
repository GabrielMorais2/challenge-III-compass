package com.moraes.gabriel.mshistory.domain.Race;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RaceHistoryRepository extends MongoRepository<RaceResult, String> {
}
