package com.rhipe.travel.booking.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@RequiredArgsConstructor
@ToString
public class TaxiBookingDTO {
    final long customerId;
    final long taxiId;
}
