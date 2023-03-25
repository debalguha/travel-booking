package com.rhipe.travel.booking;

import com.rhipe.travel.booking.dto.BookingInfoDTO;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class SagaRouteBuilder extends RouteBuilder {
    final String flightBookingEndpoint;
    final String hotelBookingEndpoint;
    final String taxiBookingEndpoint;

    public SagaRouteBuilder(@Value("${flight.api}") String flightBookingEndpoint, @Value("${hotel.api}") String hotelBookingEndpoint, @Value("${taxi.api}") String taxiBookingEndpoint) {
        this.flightBookingEndpoint = flightBookingEndpoint;
        this.hotelBookingEndpoint = hotelBookingEndpoint;
        this.taxiBookingEndpoint = taxiBookingEndpoint;
    }

    @Override
    public void configure() {
        rest()
                .id("saga-starter")
                .bindingMode(RestBindingMode.json)
                .consumes("application/json")
                .type(BookingInfoDTO.class)
                .to("direct:booking-saga");

        from("direct:booking-saga")
                .saga()
                .timeout(5, TimeUnit.SECONDS)
                .to("direct:flight-saga")
                .to("direct:hotel-saga")
                .to("direct:taxi-saga");

        from("direct:flight-saga")
                .to("rest:post:"+flightBookingEndpoint)
                .end();

        from("direct:hotel-saga")
                .to("rest:post:"+hotelBookingEndpoint)
                .end();

        from("direct:taxi-saga")
                .to("rest:post:"+taxiBookingEndpoint)
                .end();


    }

}
