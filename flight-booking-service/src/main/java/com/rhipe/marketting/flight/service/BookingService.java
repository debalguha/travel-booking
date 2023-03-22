package com.rhipe.marketting.flight.service;

import com.rhipe.marketting.flight.model.Booking;
import com.rhipe.marketting.flight.repository.BookingRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class BookingService {
    final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public Booking bookFlight(Booking booking) {
        return bookingRepository.save(booking);
    }

    public boolean cancelBooking(long bookingId) {
        bookingRepository.deleteById(bookingId);
        return true;
    }
}
