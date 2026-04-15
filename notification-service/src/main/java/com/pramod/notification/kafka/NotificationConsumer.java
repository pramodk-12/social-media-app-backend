package com.pramod.notification.kafka;

import com.pramod.common.events.CommentCreatedEvent;
import com.pramod.common.events.FollowCreatedEvent;
import com.pramod.common.events.PostLikedEvent;
import com.pramod.notification.entities.NotificationEntity;
import com.pramod.notification.respositories.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.Instant;


@Service
@RequiredArgsConstructor
public class NotificationConsumer {
    private static NotificationRepository notificationRepository;

    @KafkaListener(topics = "follow.created", groupId = "notification-service-group")
    public void handleFollow(FollowCreatedEvent event) {

        NotificationEntity notification = new NotificationEntity();
        notification.setUserId(event.getFollowingId());
        notification.setType("FOLLOW");
        notification.setActorId(event.getFollowerId());
        notification.setMessage("started following you");
        notification.setRead(false);
        notification.setCreatedAt(Instant.now().toString());

        notificationRepository.save(notification);
    }

    @KafkaListener(topics = "post.liked", groupId = "notification-group")
    public void handleLike(PostLikedEvent event) {

        NotificationEntity notification = new NotificationEntity();
        notification.setUserId(event.getAuthorId());
        notification.setType("LIKE");
        notification.setActorId(event.getUserId());
        notification.setPostId(event.getPostId());
        notification.setMessage("liked your post");
        notification.setRead(false);
        notification.setCreatedAt(Instant.now().toString());

        notificationRepository.save(notification);
    }

    @KafkaListener(topics = "comment.created", groupId = "notification-group")
    public void handleComment(CommentCreatedEvent event) {

        NotificationEntity notification = new NotificationEntity();
        notification.setUserId(event.getAuthorId());
        notification.setType("COMMENT");
        notification.setActorId(event.getUserId());
        notification.setPostId(event.getPostId());
        notification.setMessage("commented on your post");
        notification.setRead(false);
        notification.setCreatedAt(Instant.now().toString());

        notificationRepository.save(notification);
    }
}
