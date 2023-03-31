package com.rhipe.travel.booking.dto;

import java.util.Set;

public record FlightBookingResponseDTO(long customerId, Set<Long> seatIds, String correlationId) {
}
