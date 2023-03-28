package com.rhipe.travel.booking;

import com.rhipe.travel.booking.dto.BookingInfoDTO;
import com.rhipe.travel.booking.dto.FlightBookingDTO;
import com.rhipe.travel.booking.dto.RoomBookingDTO;
import com.rhipe.travel.booking.dto.TaxiBookingDTO;
import com.rhipe.travel.booking.transformers.FlightBookingTransformer;
import com.rhipe.travel.booking.transformers.HotelBookingTransformer;
import com.rhipe.travel.booking.transformers.TaxiBookingTransformer;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.TransformerBuilder;
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

        flightTransformer();
        hotelTransformer();
        taxiTransformer();

        rest()
                .id("saga-starter")
                .bindingMode(RestBindingMode.json)
                .post("/book")
                .consumes("application/json")
                .type(BookingInfoDTO.class)
                .to("direct:booking-saga");

        from("direct:booking-saga")
                .inputType(BookingInfoDTO.class)
                .saga()
                .timeout(5, TimeUnit.SECONDS)
                .to("direct:flight-saga")
                .to("direct:hotel-saga")
                .to("direct:taxi-saga");

        from("direct:flight-saga")
                .inputType(FlightBookingDTO.class)
                .to(flightBookingEndpoint)
                .end();

        from("direct:hotel-saga")
                .inputType(RoomBookingDTO.class)
                .to(hotelBookingEndpoint)
                .end();

        from("direct:taxi-saga")
                .inputType(TaxiBookingDTO.class)
                .to(taxiBookingEndpoint)
                .end();


    }

    TransformerBuilder flightTransformer() {
        return transformer()
                .fromType(BookingInfoDTO.class)
                .toType(FlightBookingDTO.class)
                .withJava(FlightBookingTransformer.class);
    }

    TransformerBuilder hotelTransformer() {
        return transformer()
                .fromType(BookingInfoDTO.class)
                .toType(RoomBookingDTO.class)
                .withJava(HotelBookingTransformer.class);
    }

    TransformerBuilder taxiTransformer() {
        return transformer()
                .fromType(BookingInfoDTO.class)
                .toType(TaxiBookingDTO.class)
                .withJava(TaxiBookingTransformer.class);
    }

}
