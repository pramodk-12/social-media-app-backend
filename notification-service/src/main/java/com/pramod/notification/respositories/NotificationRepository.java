package com.pramod.notification.respositories;

import com.pramod.notification.entities.NotificationEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NotificationRepository extends MongoRepository<NotificationEntity, String> {
    List<NotificationEntity> findByUserIdOrderByCreatedAtDesc(String userId);
}
