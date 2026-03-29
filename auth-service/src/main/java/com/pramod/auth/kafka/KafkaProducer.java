package com.pramod.auth.kafka;

import com.pramod.common.events.UserCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    private final KafkaTemplate<String, UserCreatedEvent> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, UserCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUserCreatedEvent(UserCreatedEvent event) {
        kafkaTemplate.send("user.created", event);
    }
}
