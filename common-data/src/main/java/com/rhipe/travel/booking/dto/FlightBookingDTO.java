package com.rhipe.travel.booking.dto;

import java.util.Set;

public record FlightBookingDTO(long customerId, Set<Long> seatIds) {
}
