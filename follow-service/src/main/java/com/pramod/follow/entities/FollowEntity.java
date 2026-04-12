package com.pramod.follow.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "follows")
public class FollowEntity {

    @Id
    private String id;

    private String followerId;   // A
    private String followingId;  // B

    private String createdAt;
}
