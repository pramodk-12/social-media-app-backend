package com.pramod.notification.controllers;

import com.pramod.notification.entities.NotificationEntity;
import com.pramod.notification.respositories.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationRepository repository;

    @GetMapping
    public ResponseEntity<?> getNotifications(
            @RequestHeader("X-User-Id") String userId
    ) {
        return ResponseEntity.ok(
                repository.findByUserIdOrderByCreatedAtDesc(userId)
        );
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<?> markAsRead(@PathVariable String id) {

        NotificationEntity notification =
                repository.findById(id).orElseThrow();

        notification.setRead(true);
        repository.save(notification);

        return ResponseEntity.ok("Marked as read");
    }
}
