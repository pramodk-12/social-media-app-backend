package com.pramod.follow.controllers;

import com.pramod.follow.services.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{userId}")
    public ResponseEntity<?> follow(
            @RequestHeader("X-User-Id") String followerId,
            @PathVariable String userId
    ) {
        followService.follow(followerId, userId);
        return ResponseEntity.ok("Followed successfully");
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> unfollow(
            @RequestHeader("X-User-Id") String followerId,
            @PathVariable String userId
    ) {
        followService.unfollow(followerId, userId);
        return ResponseEntity.ok("Unfollowed successfully");
    }

    @GetMapping("/followers/{userId}")
    public ResponseEntity<?> getFollowers(@PathVariable String userId) {
        return ResponseEntity.ok(followService.getFollowers(userId));
    }

    @GetMapping("/following/{userId}")
    public ResponseEntity<?> getFollowing(@PathVariable String userId) {
        return ResponseEntity.ok(followService.getFollowing(userId));
    }
}
