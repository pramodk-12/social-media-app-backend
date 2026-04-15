package com.pramod.post.kafka;

import com.pramod.post.entities.OutboxEvent;
import com.pramod.post.repositories.OutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxPublisher {

    private final OutboxRepository outboxRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Scheduled(fixedRate = 5000)
    public void publishEvents() {

        List<OutboxEvent> events = outboxRepository.findByStatus("NEW");

        for (OutboxEvent event : events) {
            try {
                kafkaTemplate.send(event.getEventType(), event.getPayload());

                event.setStatus("SENT");
                outboxRepository.save(event);

            } catch (Exception e) {
                log.error("e: ", e);
            }
        }
    }
}
