package com.digi.apigateway.filter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AccountPostFilter extends AbstractGatewayFilterFactory<AccountPostFilter.Config> {

    public AccountPostFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {

        log.info("Inside Account post filter");
        return (exchange, chain) -> chain.filter(exchange).then(Mono.fromRunnable(() -> {
            log.info("Intercepted the Account Response");
            ServerHttpResponse response = exchange.getResponse();
            HttpHeaders headers = response.getHeaders();
            headers.forEach((key, value) -> {
                log.info("Key value pair is key: {} and value: {}", key, value);
            });
        }));
    }

    @Getter
    @Setter
    public static class Config {
        private String name;

    }
}

