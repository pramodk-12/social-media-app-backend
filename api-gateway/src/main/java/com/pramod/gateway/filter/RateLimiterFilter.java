package com.pramod.gateway.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RateLimiterFilter implements GlobalFilter {

    private final ReactiveStringRedisTemplate redisTemplate;

    private static final int LIMIT = 10;
    private static final int WINDOW = 60;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String ip = exchange.getRequest()
                .getRemoteAddress()
                .getAddress()
                .getHostAddress();
        String key = "rate_limit:" +ip;
        return redisTemplate.opsForValue().increment(key)
                .flatMap(count -> {
                    if(count == 1) {
                        return redisTemplate.expire(key, Duration.ofSeconds(WINDOW))
                                .then(chain.filter(exchange));
                    }
                    if(count > LIMIT) {
                        exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
                        return exchange.getResponse().setComplete();
                    }

                    return chain.filter(exchange);
                });
    }


}
