package com.pramod.notification.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "notifications")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationEntity {

    @Id
    private String id;

    private String userId;      // receiver
    private String type;        // FOLLOW, LIKE, COMMENT
    private String message;

    private String actorId;     // who triggered
    private String postId;      // optional

    private boolean isRead;
    private String createdAt;
}