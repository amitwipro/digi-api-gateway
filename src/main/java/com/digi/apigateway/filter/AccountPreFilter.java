package com.digi.apigateway.filter;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Slf4j
@Component
public class AccountPreFilter extends AbstractGatewayFilterFactory<AccountPreFilter.Config> {

    public AccountPreFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {

        String auth = "digi" + ":" + "digi";
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
        String basic = "Basic "+encodedAuth;

        log.info("Inside Account Pre Filter");
        return (exchange, chain) -> {
            log.info("Intercepted request to account");
            ServerHttpRequest request = exchange.getRequest().mutate()
                    .header("Authorization", basic)
                    .build();
            return chain.filter(exchange.mutate().request(request).build());
        };
    }

    @Getter
    @Setter
    public static class Config {
        private String name;
    }
}
