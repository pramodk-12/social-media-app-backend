package com.pramod.post.services.impl;

import com.pramod.post.dto.PostDto;
import com.pramod.post.entities.PostEntity;
import com.pramod.post.repositories.PostRepository;
import com.pramod.post.services.PostService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostEntity createPost(PostDto dto) {
        PostEntity post = PostEntity.builder()
                .userId(dto.getUserId())
                .caption(dto.getCaption())
                .imageUrl(dto.getImageUrl())
                .createdAt(String.valueOf(System.currentTimeMillis()))
                .build();
        return postRepository.save(post);
    }

    @Override
    public List<PostEntity> getPostsByUser(String userId) {
        return postRepository.findByUserId(userId);
    }

    @Override
    public PostEntity getPostById(String postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with ID: " + postId));
    }

    @Override
    public void deletePost(String postId) {
        postRepository.deleteById(postId);
    }
}
