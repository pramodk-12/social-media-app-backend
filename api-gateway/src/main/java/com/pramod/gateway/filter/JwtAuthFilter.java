package com.pramod.gateway.filter;

import com.pramod.common.security.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter implements GlobalFilter {
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        if (path.startsWith("/auth/")) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return unauthorized(exchange, "Missing or invalid Authorization header");
        }

        try {
            String token = authHeader.substring(7);

            String username = jwtTokenUtil.extractUsername(token);
            String userId = jwtTokenUtil.extractUserId(token);

            if (!jwtTokenUtil.isTokenValid(token, username)) {
                return unauthorized(exchange, "Invalid token");
            }

            // 🔥 Skip mutation for multipart
            if (exchange.getRequest().getHeaders().getContentType() != null &&
                    exchange.getRequest().getHeaders().getContentType()
                            .isCompatibleWith(org.springframework.http.MediaType.MULTIPART_FORM_DATA)) {
                return chain.filter(exchange);
            }

            ServerWebExchange mutatedExchange = exchange.mutate()
                    .request(exchange.getRequest().mutate()
                            .header("X-User-Name", username)
                            .header("X-User-Id", userId)
                            .build())
                    .build();

            return chain.filter(mutatedExchange);

        } catch (Exception e) {
            return unauthorized(exchange, "Token expired or invalid");
        }
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange, String message) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);

        byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);

        return exchange.getResponse().writeWith(Mono.just(buffer));
    }

}