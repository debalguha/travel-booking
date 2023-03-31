package com.rhipe.marketting.flight.service;

import com.rhipe.marketting.flight.model.Booking;
import com.rhipe.marketting.flight.model.Seat;
import com.rhipe.marketting.flight.repository.BookingRepository;
import com.rhipe.marketting.flight.repository.SeatRepository;
import com.rhipe.travel.booking.dto.FlightBookingDTO;
import com.rhipe.travel.booking.dto.FlightBookingResponseDTO;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class BookingService {
    final BookingRepository bookingRepository;
    final SeatRepository seatRepository;

    public BookingService(BookingRepository bookingRepository, SeatRepository seatRepository) {
        this.bookingRepository = bookingRepository;
        this.seatRepository = seatRepository;
    }

    public FlightBookingResponseDTO bookFlight(FlightBookingDTO booking, String correlationId) {
        log.info("Booking {} on request {}", booking, correlationId);
        Booking flightBooking = bookingRepository.save(new Booking(booking.customerId(), new HashSet<>(seatRepository.findAllById(booking.seatIds())), correlationId));
        return new FlightBookingResponseDTO(flightBooking.getCustomerId(), flightBooking.getSeats().stream().map(Seat::getId).collect(Collectors.toSet()), flightBooking.getCorrelationId());
    }

    public boolean cancelBooking(String correlationId) {
        bookingRepository.deleteByCorrelationId(correlationId);
        return true;
    }

    public List<FlightBookingResponseDTO> allBookings() {
        return bookingRepository.findAll()
                .stream()
                .map(b -> new FlightBookingResponseDTO(b.getCustomerId(), b.getSeats().stream().map(Seat::getId).collect(Collectors.toSet()), b.getCorrelationId()))
                .collect(Collectors.toList());
    }
}
