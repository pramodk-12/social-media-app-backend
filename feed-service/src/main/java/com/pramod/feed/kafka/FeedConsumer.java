package com.pramod.feed.kafka;

import com.pramod.common.events.PostCreatedEvent;
import com.pramod.feed.services.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedConsumer {
    private final FeedService feedService;

    @KafkaListener(topics = "post.created", groupId = "feed-service-group")
    public void consume(PostCreatedEvent event) {
        feedService.processPost(event);
    }
}
