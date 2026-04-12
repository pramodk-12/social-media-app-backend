package com.pramod.follow.services;

import java.util.List;

public interface FollowService {
    public void follow(String followerId, String followingId);
    public void unfollow(String followerId, String followingId);
    public List<String> getFollowers(String userId);
    public List<String> getFollowing(String userId);
}
