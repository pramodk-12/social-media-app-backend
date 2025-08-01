package com.pramod.user.kafka;

import com.pramod.events.UserCreatedEvent;
import com.pramod.user.dto.UserDto;
import com.pramod.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @Autowired
    private UserService userService;

    @KafkaListener(topics = "user.created", groupId = "user-service-group")
    public void handleUserCreated(UserCreatedEvent event) {
        UserDto dto = new UserDto();
        dto.setUsername(event.getUsername());
        dto.setEmail(event.getEmail());
        dto.setAccountCreatedAt(event.getCreatedAt());
        dto.setBio("My Bio");
        userService.createUser(dto);
    }
}
