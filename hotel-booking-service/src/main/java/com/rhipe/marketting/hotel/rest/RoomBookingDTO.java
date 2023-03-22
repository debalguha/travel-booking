package com.rhipe.marketting.hotel.rest;

import java.util.Set;

public record RoomBookingDTO(long customerId, Set<Long> roomId) {}
