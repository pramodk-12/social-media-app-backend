package com.pramod.feed.controllers;

import com.pramod.feed.repositories.FeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feed")
public class FeedController {
    private final FeedRepository feedRepository;

    @GetMapping
    public ResponseEntity<?> getFeed(
            @RequestHeader("X-User-Id") String userId
    ) {
        return ResponseEntity.ok(
                feedRepository.findByUserIdOrderByCreatedAtDesc(userId)
        );
    }
}
