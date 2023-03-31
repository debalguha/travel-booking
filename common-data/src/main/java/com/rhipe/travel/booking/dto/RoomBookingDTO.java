package com.rhipe.travel.booking.dto;

import java.util.Set;

public record RoomBookingDTO(long customerId, Set<Long> roomId) {}
