package com.pramod.common.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentCreatedEvent {
    private String userId;
    private String postId;
    private String authorId;
    private String comment;
}
