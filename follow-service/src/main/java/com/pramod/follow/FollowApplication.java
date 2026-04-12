package com.pramod.follow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = "com.pramod.follow")
@EnableMongoRepositories(basePackages = "com.pramod.follow.repositories")
@EntityScan(basePackages = "com.pramod.follow.entities")
public class FollowApplication {
    public static void main(String[] args) {
        SpringApplication.run(FollowApplication.class, args);
    }
}