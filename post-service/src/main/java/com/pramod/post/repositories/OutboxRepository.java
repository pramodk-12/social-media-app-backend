package com.pramod.post.repositories;

import com.pramod.post.entities.OutboxEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OutboxRepository extends MongoRepository<OutboxEvent, String> {

    List<OutboxEvent> findByStatus(String status);
}