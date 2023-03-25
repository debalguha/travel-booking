package com.rhipe.marketting.flight.config;

import com.rhipe.marketting.flight.model.Booking;
import com.rhipe.marketting.flight.service.BookingService;
import com.rhipe.marketting.flight.service.FlightService;
import com.rhipe.travel.booking.dto.FlightBookingDTO;
import com.rhipe.travel.booking.dto.FlightResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.SagaPropagation;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class BookingRouteBuilder extends RouteBuilder {

    final BookingService bookingService;
    final FlightService flightService;

    @Override
    public void configure() throws Exception {
        log.info("Building route!!");

        rest()
                .bindingMode(RestBindingMode.json)
                .get("/flight/{name}")
                .outType(FlightResponseDTO.class)
                .param().name("name").type(RestParamType.path).description("The name of the flight").dataType("string").endParam()
                .responseMessage().code(200).message("User successfully returned").endResponseMessage()
                .to("bean:flightService?method=lookupFlight(${header.name})");

        rest()
                .id("flight-booking-route")
                .consumes("application/json")
                .post("/flight/book")
                .type(FlightBookingDTO.class)
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
        log.info("Finished Building route!!");

    }
}
