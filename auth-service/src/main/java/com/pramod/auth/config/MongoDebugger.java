package com.pramod.auth.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class MongoDebugger {

    @Autowired
    private MongoTemplate mongoTemplate;

    @PostConstruct
    public void printDatabaseName() {
        System.out.println("✅ Using MongoDB database: " + mongoTemplate.getDb().getName());
    }
}

