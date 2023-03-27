package com.rhipe.travel.booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class App {
    public static void main( String[] args ) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/travel")
                        .uri("http://sbtravelsv/api/"))
                .route(p -> p
                        .path("/flight")
                        .uri("http://sbflightsv/api/"))
                .route(p -> p
                        .path("/taxi")
                        .uri("http://sbtaxisv/api/"))
                .route(p -> p
                        .path("/hotel")
                        .uri("http://sbhotelsv/api/"))
                .build();
    }
}
