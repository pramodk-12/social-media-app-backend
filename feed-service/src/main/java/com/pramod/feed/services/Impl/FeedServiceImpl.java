package com.pramod.feed.services.Impl;

import com.pramod.common.events.PostCreatedEvent;
import com.pramod.feed.entities.FeedEntity;
import com.pramod.feed.entities.FollowCache;
import com.pramod.feed.repositories.FeedRepository;
import com.pramod.feed.repositories.FollowCacheRepository;
import com.pramod.feed.services.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {
    private final FeedRepository feedRepository;
    private final FollowCacheRepository followCacheRepository;

    public void processPost(PostCreatedEvent event) {

        // 1. Get followers
        List<String> followers = followCacheRepository
                .findByUserId(event.getUserId())
                .map(FollowCache::getFollowers)
                .orElse(Collections.emptyList());

        // 2. Create feed entries
        List<FeedEntity> feeds = followers.stream()
                .map(followerId -> {
                    FeedEntity feed = new FeedEntity();
                    feed.setUserId(followerId);
                    feed.setPostId(event.getPostId());
                    feed.setAuthorId(event.getUserId());
                    feed.setCaption(event.getCaption());
                    feed.setCreatedAt(event.getCreatedAt());
                    return feed;
                })
                .toList();

        // 3. Batch insert
        feedRepository.saveAll(feeds);
    }
}
