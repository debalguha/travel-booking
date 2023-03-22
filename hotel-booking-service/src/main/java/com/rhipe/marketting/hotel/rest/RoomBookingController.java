package com.rhipe.marketting.hotel.rest;

import com.rhipe.marketting.hotel.service.BookingService;
import lombok.RequiredArgsConstructor;

//@RestController("/hotel")
@RequiredArgsConstructor
public class RoomBookingController {
    final BookingService bookingService;

/*    @RequestMapping("/booking")
    Booking bookFlight(@RequestBody RoomBookingDTO roomBookingDTO) {
        return bookingService.bookRoom(roomBookingDTO);
    }

    @RequestMapping("/booking/compensate/{bookingId}")
    boolean compensate(@PathVariable String bookingId) {
        return bookingService.compensate(Long.parseLong(bookingId));
    }*/
}
