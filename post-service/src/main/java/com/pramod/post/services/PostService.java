package com.pramod.post.services;

import com.pramod.post.dto.PostDto;
import com.pramod.post.entities.PostEntity;
import java.util.List;

public interface PostService {
    PostEntity createPost(PostDto dto);
    List<PostEntity> getPostsByUser(String userId);
    PostEntity getPostById(String postId);
    void deletePost(String postId);
}
