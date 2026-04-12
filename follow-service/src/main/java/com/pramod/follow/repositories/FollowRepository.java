package com.pramod.follow.repositories;

import com.pramod.follow.entities.FollowEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends MongoRepository<FollowEntity, String> {

    List<FollowEntity> findByFollowerId(String followerId);

    List<FollowEntity> findByFollowingId(String followingId);

    Optional<FollowEntity> findByFollowerIdAndFollowingId(String followerId, String followingId);

    void deleteByFollowerIdAndFollowingId(String followerId, String followingId);
}
