package com.pramod.feed.services;

import com.pramod.common.events.PostCreatedEvent;

public interface FeedService {
    public void processPost(PostCreatedEvent event);

}
