package com.rhipe.travel.booking.dto;

import java.util.Set;

public record HotelResponseDTO(long id, String hotelName, Set<RoomResponseDTO> rooms) {
}
