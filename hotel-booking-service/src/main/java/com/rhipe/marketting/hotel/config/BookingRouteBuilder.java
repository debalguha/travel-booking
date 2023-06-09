package com.rhipe.marketting.hotel.config;

import com.rhipe.marketting.hotel.model.Booking;
import com.rhipe.travel.booking.dto.HotelResponseDTO;
import com.rhipe.marketting.hotel.service.BookingService;
import com.rhipe.marketting.hotel.service.HotelService;
import com.rhipe.travel.booking.dto.RoomBookingDTO;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.SagaPropagation;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookingRouteBuilder extends RouteBuilder {

    final BookingService bookingService;
    final HotelService hotelService;

    @Override
    public void configure() throws Exception {
        log.info("Building route!!");

        rest()
                .bindingMode(RestBindingMode.json)
                .get("/hotel/{name}")
                .outType(HotelResponseDTO.class)
                .param().name("name").type(RestParamType.path).description("The name of the Hotel").dataType("string").endParam()
                .responseMessage().code(200).message("Hotel successfully returned").endResponseMessage()
                .to("bean:hotelService?method=lookupHotel(${header.name})");

        rest()
                .bindingMode(RestBindingMode.json)
                .get("/hotel/names")
                .outType(List.class)
                .responseMessage().code(200).message("User successfully returned").endResponseMessage()
                .to("bean:hotelService?method=hotelNames");

        rest()
                .id("hotel-booking-route")
                .consumes("application/json")
                .post("/room/book")
                .bindingMode(RestBindingMode.json)
                .type(RoomBookingDTO.class)
                .to("direct:hotel-saga");

        from("direct:hotel-saga")
                .saga()
                .propagation(SagaPropagation.MANDATORY)
                .compensation("direct:cancel-booking")
                    .transform().header(Exchange.SAGA_LONG_RUNNING_ACTION)
                    .bean(bookingService, "bookRoom")
                    .log("Room booking ${body} created");

        from("direct:cancel-booking")
                .transform().header(Exchange.SAGA_LONG_RUNNING_ACTION)
                .bean(bookingService, "cancelBooking")
                .log("Room booking ${body} cancel");
        log.info("Finished Building route!!");
    }
}
