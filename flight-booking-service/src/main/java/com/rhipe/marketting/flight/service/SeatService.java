package com.rhipe.marketting.flight.service;

import com.rhipe.marketting.flight.model.Seat;
import com.rhipe.marketting.flight.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SeatService {
    final SeatRepository seatRepository;

    public Optional<Seat> lookupSeat(long seatId) {
        return seatRepository.findById(seatId);
    }
}
