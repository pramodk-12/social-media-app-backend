package com.pramod.common.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowCreatedEvent {
    private String followerId;
    private String followingId;
}
