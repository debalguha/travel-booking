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
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class BookingRouteBuilder extends RouteBuilder {

    final BookingService bookingService;
    final FlightService flightService;

    @Override
    public void configure() throws Exception {
        log.info("Building route!!");
        restConfiguration().component("servlet").bindingMode(RestBindingMode.auto);
        rest()
                .bindingMode(RestBindingMode.json)
                .get("/flight/{name}")
                .outType(FlightResponseDTO.class)
                .param().name("name").type(RestParamType.path).description("The name of the flight").dataType("string").endParam()
                .to("bean:flightService?method=lookupFlight(${header.name})");

        rest()
                .bindingMode(RestBindingMode.json)
                .get("/flight/names")
                .outType(List.class)
                .to("bean:flightService?method=flightNames");

        rest()
                .bindingMode(RestBindingMode.json)
                .get("/flight/bookings/all")
                .outType(List.class)
                .to("bean:bookingService?method=allBookings");

        rest()
                .id("flight-booking-route")
                .consumes("application/json")
                .post("/flight/book")
                .param().type(RestParamType.header).name("correlation-id").required(true).endParam()
                .bindingMode(RestBindingMode.json)
                .type(FlightBookingDTO.class)
                .outType(FlightBookingDTO.class)
                .to("direct:flight-saga");

        from("direct:flight-saga")
                .id("flight-saga-route")
                .saga()
                    .propagation(SagaPropagation.MANDATORY)
                    .option("correlation-id", header("correlation-id"))
                    .compensation("direct:cancel-booking")
                    .timeout(1, TimeUnit.MINUTES)
                    //.onCompletion().end()
                .bean(bookingService, "bookFlight(${body}, ${header.correlation-id})")
                .log("Flight booking ${body} created");



        from("direct:cancel-booking")
                .id("flight-cancel-route")
                .bean(bookingService, "cancelBooking(${header.correlation-id})")
                .log("Flight booking ${body} cancel");
        log.info("Finished Building route!!");

    }
}
