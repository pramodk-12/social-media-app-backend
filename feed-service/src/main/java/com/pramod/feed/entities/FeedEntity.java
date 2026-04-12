package com.pramod.feed.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "feeds")
@Data
public class FeedEntity {

    @Id
    private String id;

    private String userId;     // whose feed
    private String postId;
    private String authorId;
    private String caption;
    private String createdAt;
}