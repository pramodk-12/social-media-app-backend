package com.pramod.auth.repositories;

import com.pramod.auth.entities.AuthUserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<AuthUserEntity, String> {
    Optional<AuthUserEntity> findByUsername(String username);
    Optional<AuthUserEntity> findByEmail(String email);
}
