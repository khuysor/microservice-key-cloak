package com.huysor.saas.gateway.config;

import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class CorrelationIdFilter implements GlobalFilter, Ordered {
    public static final String CORRELATION_ID_HEADER = "X-Correlation-ID";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        String correlationId = request.getHeaders()
                .getFirst(CORRELATION_ID_HEADER);

        if (correlationId == null || correlationId.isEmpty()) {
            correlationId = UUID.randomUUID().toString();
            ServerHttpRequest mutatedRequest = request.mutate()
                    .header(CORRELATION_ID_HEADER, correlationId)
                    .build();

            exchange = exchange.mutate()
                    .request(mutatedRequest)
                    .build();
        }

        // Optional: Add to MDC for logging
        MDC.put(CORRELATION_ID_HEADER, correlationId);

        return chain.filter(exchange)
                .doFinally(signalType -> MDC.remove(CORRELATION_ID_HEADER));
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
