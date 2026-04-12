package com.pramod.follow.services.Impl;

import com.pramod.follow.entities.FollowEntity;
import com.pramod.follow.repositories.FollowRepository;
import com.pramod.follow.services.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {
    private final FollowRepository followRepository;

    public void follow(String followerId, String followingId) {

        if (followerId.equals(followingId)) {
            throw new RuntimeException("You cannot follow yourself");
        }

        boolean exists = followRepository
                .findByFollowerIdAndFollowingId(followerId, followingId)
                .isPresent();

        if (exists) {
            return; // already following
        }

        FollowEntity follow = new FollowEntity();
        follow.setFollowerId(followerId);
        follow.setFollowingId(followingId);
        follow.setCreatedAt(Instant.now().toString());

        followRepository.save(follow);
    }

    public void unfollow(String followerId, String followingId) {
        followRepository.deleteByFollowerIdAndFollowingId(followerId, followingId);
    }

    public List<String> getFollowers(String userId) {
        return followRepository.findByFollowingId(userId)
                .stream()
                .map(FollowEntity::getFollowerId)
                .toList();
    }

    public List<String> getFollowing(String userId) {
        return followRepository.findByFollowerId(userId)
                .stream()
                .map(FollowEntity::getFollowingId)
                .toList();
    }
}
