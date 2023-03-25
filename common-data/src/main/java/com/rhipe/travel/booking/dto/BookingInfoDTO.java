package com.rhipe.travel.booking.dto;

import java.util.Set;

public record BookingInfoDTO(long customerId, Set<Long> flightSeatIds, Set<Long> hotelRoomIds, long taxiId) {
}
