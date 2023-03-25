package com.rhipe.marketting.flight.service;

import com.rhipe.marketting.flight.model.Booking;
import com.rhipe.marketting.flight.repository.BookingRepository;
import com.rhipe.marketting.flight.repository.SeatRepository;
import com.rhipe.travel.booking.dto.FlightBookingDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@Transactional
public class BookingService {
    final BookingRepository bookingRepository;
    final SeatRepository seatRepository;

    public BookingService(BookingRepository bookingRepository, SeatRepository seatRepository) {
        this.bookingRepository = bookingRepository;
        this.seatRepository = seatRepository;
    }

    public Booking bookFlight(FlightBookingDTO booking) {
        return bookingRepository.save(new Booking(booking.customerId(), new HashSet<>(seatRepository.findAllById(booking.seatIds()))));
    }

    public boolean cancelBooking(long bookingId) {
        bookingRepository.deleteById(bookingId);
        return true;
    }
}
