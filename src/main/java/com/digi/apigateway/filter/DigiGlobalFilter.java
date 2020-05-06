package com.digi.apigateway.filter;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DigiGlobalFilter extends AbstractGatewayFilterFactory<DigiGlobalFilter.Config> {

    public DigiGlobalFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {

        log.info("Inside Digi Global Filter");
        return (exchange, chain) -> {
            log.info("Intercept request and set header...");
            ServerHttpRequest request = exchange.getRequest().mutate()
                    .header("access-token", "any access token").build();
            return chain.filter(exchange.mutate().request(request).build());
        };
    }

    @Getter
    @Setter
    public static class Config {
        private String name;
    }
}
