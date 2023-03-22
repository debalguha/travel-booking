package com.rhipe.marketting.flight.rest;

import com.rhipe.marketting.flight.service.BookingService;
import com.rhipe.marketting.flight.service.SeatService;
import lombok.RequiredArgsConstructor;

//@RestController("/flight")
@RequiredArgsConstructor
public class FlightBookingController {
    final BookingService bookingService;

    final SeatService seatService;

/*    @RequestMapping("/booking")
    Booking bookFlight(@RequestBody FlightBookingDTO flightBookingDTO) {
        Set<Optional<Seat>> seats = flightBookingDTO.seatIds()
                .stream()
                .map(seatService::lookupSeat)
                .collect(Collectors.toSet());
        return bookingService.bookFlight(new Booking(flightBookingDTO.customerId(), unWrap(seats)));
    }

    @RequestMapping("/booking/compensate/{bookingId}")
    boolean compensate(@PathVariable String bookingId) {
        return bookingService.compensate(Long.parseLong(bookingId));
    }*/
}
