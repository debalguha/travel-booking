package com.rhipe.marketting.flight.rest;

import java.util.Set;

public record FlightBookingDTO(long customerId, Set<Long> seatIds) {}
