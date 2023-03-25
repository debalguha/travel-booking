package com.rhipe.travel.booking.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Set;

@Data
@RequiredArgsConstructor
@ToString
public class RoomBookingDTO {
    final long customerId;
    final Set<Long> roomId;
}
