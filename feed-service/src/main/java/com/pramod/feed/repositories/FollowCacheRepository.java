package com.pramod.feed.repositories;

import com.pramod.feed.entities.FollowCache;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface FollowCacheRepository extends MongoRepository<FollowCache, String> {
    Optional<FollowCache> findByUserId(String userId);
}
