package com.rhipe.travel.booking.dto;

import java.util.Set;

public record RoomBookingResponseDTO(long customerId, Set<Long> roomId, String correlationId) {
}
