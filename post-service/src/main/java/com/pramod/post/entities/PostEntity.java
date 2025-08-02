package com.pramod.post.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "posts")
public class PostEntity {
    @Id
    private String id;
    private String userId;
    private String caption;
    private String location;
    private String imageUrl;
    private String createdAt;
}

