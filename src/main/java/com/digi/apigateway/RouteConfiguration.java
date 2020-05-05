package com.digi.apigateway;


import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfiguration {

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes().build();
    }


    @Bean
    public RouteLocator restaurantRoute(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/digi/retailbank/v1/account/**")
                        .filters(f -> f.hystrix(config -> config
                                .setName("retail-account")
                                .setFallbackUri("forward:/fallback/accountFallback")))
                        .uri("http://localhost:8002/"))
                .build();
    }

}
