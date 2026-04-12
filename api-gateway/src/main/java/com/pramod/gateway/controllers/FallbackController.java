package com.pramod.gateway.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @RequestMapping("/user")
    public Mono<ResponseEntity<String>> userFallback(ServerHttpRequest request) {
        String method = request.getMethod().name();
        String path = request.getURI().getPath();

        return Mono.just(ResponseEntity
                .status(503)
                .body("User service is down. Method: " + method + ", Path: " + path));
    }

    @RequestMapping("/post")
    public Mono<ResponseEntity<String>> postFallback(ServerHttpRequest request) {
        String method = request.getMethod().name();
        String path = request.getURI().getPath();

        return Mono.just(ResponseEntity
                .status(503)
                .body("Post service is down. Method: " + method + ", Path: " + path));
    }

}
