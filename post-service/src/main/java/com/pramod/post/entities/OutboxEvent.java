package com.pramod.post.entities;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "outbox_events")
@Data
@Builder
public class OutboxEvent {

    @Id
    private String id;

    private String aggregateType; // POST
    private String aggregateId;   // postId

    private String eventType;     // post.created
    private String payload;       // JSON

    private String status;        // NEW, SENT

    private String createdAt;
}
