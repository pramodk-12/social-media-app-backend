package com.pramod.post.controllers;

import com.pramod.post.dto.PostDto;
import com.pramod.post.entities.PostEntity;
import com.pramod.post.services.PostService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Value("${post.upload.dir}")
    private String uploadDir;

    @PostMapping(value = "/create", consumes = "multipart/form-data")
    public ResponseEntity<PostEntity> createPost(
            @RequestParam("image") MultipartFile image,
            @RequestParam("userId") String userId,
            @RequestParam("caption") String caption
    ) throws IOException {
        // Validate image
        if (image.isEmpty() || image.getOriginalFilename() == null) {
            return ResponseEntity.badRequest().build();
        }

        // Clean file name
        String filename = StringUtils.cleanPath(image.getOriginalFilename());

        // Save to upload dir
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(filename);
        Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Build public URL
        String imageUrl = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/images/")
                .path(filename)
                .toUriString();

        // Create Post DTO
        PostDto postDto = PostDto.builder()
                .userId(userId)
                .caption(caption)
                .imageUrl(imageUrl)
                .build();

        PostEntity post = postService.createPost(postDto);
        return ResponseEntity.ok(post);
    }

    @GetMapping("/user/{userId}")
    public List<PostEntity> getPostsByUser(@PathVariable String userId) {
        return postService.getPostsByUser(userId);
    }

    @GetMapping("/{postId}")
    public PostEntity getPostById(@PathVariable String postId) {
        return postService.getPostById(postId);
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable String postId) {
        postService.deletePost(postId);
    }
}
