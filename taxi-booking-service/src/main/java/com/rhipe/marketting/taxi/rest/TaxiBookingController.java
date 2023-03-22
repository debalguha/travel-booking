package com.rhipe.marketting.taxi.rest;

import com.rhipe.marketting.taxi.service.BookingService;
import com.rhipe.marketting.taxi.service.TaxiService;
import lombok.RequiredArgsConstructor;

//@RestController("/flight")
@RequiredArgsConstructor
public class TaxiBookingController {
    final BookingService bookingService;

    final TaxiService seatService;

/*    @RequestMapping("/booking")
    Booking bookFlight(@RequestBody TaxiBookingDTO taxiBookingDTO) {
        return bookingService.bookTaxi(taxiBookingDTO)
                .orElseThrow(() -> new RuntimeException("Unable to find a taxi to book"));
    }

    @RequestMapping("/booking/compensate/{bookingId}")
    boolean compensate(@PathVariable String bookingId) {
        return bookingService.compensate(Long.parseLong(bookingId));
    }*/
}
