package com.rhipe.marketting.taxi.config;

import com.rhipe.marketting.taxi.model.Booking;
import com.rhipe.marketting.taxi.service.TaxiService;
import com.rhipe.travel.booking.dto.TaxiBookingDTO;
import com.rhipe.travel.booking.dto.TaxiResponseDTO;
import com.rhipe.marketting.taxi.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.SagaPropagation;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class BookingRouteBuilder extends RouteBuilder {

    final BookingService bookingService;
    final TaxiService taxiService;

    @Override
    public void configure() throws Exception {

        rest()
                .bindingMode(RestBindingMode.json)
                .get("/taxi/{name}")
                .outType(TaxiResponseDTO.class)
                .param().name("name").type(RestParamType.path).description("The name of the Taxi").dataType("string").endParam()
                .responseMessage().code(200).message("Taxi successfully returned").endResponseMessage()
                .to("bean:taxiService?method=lookupTaxi(${header.name})");

        rest()
                .bindingMode(RestBindingMode.json)
                .get("/taxi/names")
                .outType(List.class)
                .responseMessage().code(200).message("User successfully returned").endResponseMessage()
                .to("bean:taxiService?method=allTaxiNums");

        rest()
                .id("taxi-booking-route")
                .consumes("application/json")
                .post("/taxi/book")
                .param().type(RestParamType.header).name("correlation-id").required(true).endParam()
                .bindingMode(RestBindingMode.json)
                .type(TaxiBookingDTO.class)
                .outType(TaxiBookingDTO.class)
                .to("direct:taxi-saga");

        from("direct:taxi-saga")
                .saga()
                    .propagation(SagaPropagation.MANDATORY)
                    .option("correlation-id", header("correlation-id"))
                    .compensation("direct:cancel-booking")
                    .timeout(1, TimeUnit.MINUTES)
                    //.onCompletion().end()
                .bean(bookingService, "bookTaxi(${body}, ${header.correlation-id})")
                .log("Taxi booking ${body} created");

        from("direct:cancel-booking")
                .bean(bookingService, "cancelBooking")
                .log("Taxi booking ${body} cancel");

    }
}
