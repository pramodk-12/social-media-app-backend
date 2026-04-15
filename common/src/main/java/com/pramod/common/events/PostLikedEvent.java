package com.pramod.common.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostLikedEvent {
    private String userId;   // who liked
    private String postId;
    private String authorId; // post owner
}
