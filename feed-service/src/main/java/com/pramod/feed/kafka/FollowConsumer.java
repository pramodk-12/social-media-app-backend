package com.pramod.feed.kafka;

import com.pramod.common.events.FollowCreatedEvent;
import com.pramod.common.events.FollowDeletedEvent;
import com.pramod.feed.entities.FollowCache;
import com.pramod.feed.repositories.FollowCacheRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class FollowConsumer {

    private final FollowCacheRepository followCacheRepository;

    @KafkaListener(topics = "follow.created", groupId = "feed-service-group")
    public void handleFollowCreated(FollowCreatedEvent event) {

        FollowCache cache = followCacheRepository
                .findByUserId(event.getFollowingId())
                .orElseGet(() -> {
                    FollowCache fc = new FollowCache();
                    fc.setUserId(event.getFollowingId());
                    fc.setFollowers(new ArrayList<>());
                    return fc;
                });

        cache.getFollowers().add(event.getFollowerId());

        followCacheRepository.save(cache);
    }

    @KafkaListener(topics = "follow.deleted", groupId = "feed-service-group")
    public void handleFollowDeleted(FollowDeletedEvent event) {

        followCacheRepository.findByUserId(event.getFollowingId())
                .ifPresent(cache -> {
                    cache.getFollowers().remove(event.getFollowerId());
                    followCacheRepository.save(cache);
                });
    }
}
