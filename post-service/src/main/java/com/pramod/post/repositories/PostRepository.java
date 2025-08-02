package com.pramod.post.repositories;

import com.pramod.post.entities.PostEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface PostRepository extends MongoRepository<PostEntity, String> {
    List<PostEntity> findByUserId(String userId);
}
