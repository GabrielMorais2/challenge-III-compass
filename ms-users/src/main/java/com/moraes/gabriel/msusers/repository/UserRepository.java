package com.moraes.gabriel.msusers.repository;

import com.moraes.gabriel.msusers.model.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserCredential, Long> {
    boolean existsByEmail(String email);
}
