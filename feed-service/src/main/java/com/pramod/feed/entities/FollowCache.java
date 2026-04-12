package com.pramod.feed.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "follow_cache")
@Data
public class FollowCache {

    @Id
    private String id;

    private String userId;           // author
    private List<String> followers;  // list of followers
}
