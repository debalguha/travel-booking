package com.rhipe.marketting.flight.config;

import com.rhipe.marketting.flight.model.Booking;
import com.rhipe.marketting.flight.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.SagaPropagation;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingRouteBuilder extends RouteBuilder {

    final BookingService bookingService;

    @Override
    public void configure() throws Exception {
        restConfiguration().port(8080);

        rest("/api")
                .id("flight-booking-route")
                .consumes("application/json")
                .post("/flight/book")
                .bindingMode(RestBindingMode.json)
                .type(Booking.class)
                .to("direct:flight-saga");

        from("direct:flight-saga")
                .saga()
                .propagation(SagaPropagation.MANDATORY)
                .compensation("direct:cancel-booking")
                    .transform().header(Exchange.SAGA_LONG_RUNNING_ACTION)
                    .bean(bookingService, "bookFlight")
                    .log("Flight booking ${body} created");

        from("direct:cancel-booking")
                .transform().header(Exchange.SAGA_LONG_RUNNING_ACTION)
                .bean(bookingService, "cancelBooking")
                .log("Flight booking ${body} cancel");

    }
}
