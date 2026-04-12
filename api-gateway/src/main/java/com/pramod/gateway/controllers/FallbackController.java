package com.pramod.gateway.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/user")
    public Mono<String> userFallback() {
        return Mono.just("User service is currently unavailable. Please try later");
    }

    @PostMapping("/post")
    public Mono<String> postFallback() {
        return Mono.just("Post service is down. Please try later.");
    }

}
