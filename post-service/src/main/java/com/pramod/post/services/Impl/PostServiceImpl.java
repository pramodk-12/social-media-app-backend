package com.pramod.post.services.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pramod.post.dto.PostDto;
import com.pramod.post.entities.OutboxEvent;
import com.pramod.post.entities.PostEntity;
import com.pramod.post.repositories.OutboxRepository;
import com.pramod.post.repositories.PostRepository;
import com.pramod.post.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final OutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;


    @Transactional // CRITICAL: Ensures Atomicity
    public PostEntity createPost(PostDto dto) {
        // 1. Create and Save the Post
        PostEntity post = PostEntity.builder()
                .userId(dto.getUserId())
                .caption(dto.getCaption())
                .imageUrl(dto.getImageUrl())
                .createdAt(Instant.now().toString())
                .build();

        PostEntity savedPost = postRepository.save(post);

        // 2. Create Outbox Event
        try {
            OutboxEvent event = OutboxEvent.builder()
                    .aggregateType("POST")
                    .aggregateId(savedPost.getId().toString())
                    .eventType("POST_CREATED")
                    .payload(objectMapper.writeValueAsString(savedPost)) // Map entity to event
                    .status("PENDING")
                    .createdAt(Instant.now().toString())
                    .build();

            outboxRepository.save(event);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize post event", e);
        }

        return savedPost;
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
