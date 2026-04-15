package com.pramod.feed.kafka;

import com.pramod.common.events.PostCreatedEvent;
import com.pramod.feed.services.FeedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedConsumer {
    private final FeedService feedService;

    @KafkaListener(topics = "post.created", groupId = "feed-service-group")
    public void consume(PostCreatedEvent event) {
        feedService.processPost(event);
    }

    @KafkaListener(topics = "post.created.dlq", groupId = "dlq-group")
    public void handleDLQ(PostCreatedEvent event) {
        log.error("Failed event: {}", event);
    }
}
