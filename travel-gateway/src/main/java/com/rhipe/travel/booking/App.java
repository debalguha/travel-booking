package com.rhipe.travel.booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class App {
    public static void main( String[] args ) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/api/travel/{segment}")
                        .uri("http://sbtravelsv/api/travel"))
                .route(p -> p
                        .path("/api/flight/{segment}")
                        .uri("http://sbflightsv/api/flight"))
                .route(p -> p
                        .path("/api/taxi/{segment}")
                        .uri("http://sbtaxisv/api/taxi"))
                .route(p -> p
                        .path("/api/hotel/{segment}")
                        .uri("http://sbhotelsv/api/hotel"))
                .build();
    }
}
