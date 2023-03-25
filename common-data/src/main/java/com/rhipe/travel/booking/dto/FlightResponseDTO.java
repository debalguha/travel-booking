package com.rhipe.travel.booking.dto;

import java.util.Set;

public record FlightResponseDTO(long id, String name, Set<SeatResponseDTO> seats) {
}
