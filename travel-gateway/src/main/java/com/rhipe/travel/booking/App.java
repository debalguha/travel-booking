package com.rhipe.travel.booking;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class App {
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/travel/{segment}")
                        .uri("http://sbtravelsv/api/"))
                .route(p -> p
                        .path("/flight/{segment}")
                        .uri("http://sbflightsv/api/"))
                .route(p -> p
                        .path("/taxi/{segment}")
                        .uri("http://sbtaxisv/api/"))
                .route(p -> p
                        .path("/hotel/{segment}")
                        .uri("http://sbhotelsv/api/"))
                .build();
    }
}
