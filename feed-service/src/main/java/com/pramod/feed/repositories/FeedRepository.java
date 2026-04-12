package com.pramod.feed.repositories;

import com.pramod.feed.entities.FeedEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FeedRepository extends MongoRepository<FeedEntity,String> {
    List<FeedEntity> findByUserIdOrderByCreatedAtDesc(String userId);
}
