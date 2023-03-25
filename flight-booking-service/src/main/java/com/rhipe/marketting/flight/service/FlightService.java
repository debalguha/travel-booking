package com.rhipe.marketting.flight.service;

import com.rhipe.marketting.flight.model.Seat;
import com.rhipe.marketting.flight.repository.FlightRepository;
import com.rhipe.travel.booking.dto.FlightResponseDTO;
import com.rhipe.travel.booking.dto.SeatResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FlightService {
    final FlightRepository flightRepository;

    public FlightResponseDTO lookupFlight(String name) {
        return flightRepository.findByName(name)
                .map(flight -> new FlightResponseDTO(flight.getId(), flight.getName(),
                                flight.getSeats()
                                        .stream()
                                        .map(this::map)
                                        .collect(Collectors.toSet())
                        )
                ).orElseThrow(() -> new RuntimeException("Can't find flight with Name:: "+name));
    }

    private SeatResponseDTO map(Seat seat) {
        return new SeatResponseDTO(seat.getId(), seat.getSeatIdentifier());
    }
}
