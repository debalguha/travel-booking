package com.rhipe.marketting.taxi.service;

import com.rhipe.marketting.taxi.model.Booking;
import com.rhipe.marketting.taxi.repository.BookingRepository;
import com.rhipe.marketting.taxi.repository.TaxiRepository;
import com.rhipe.travel.booking.dto.TaxiBookingDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class BookingService {
    final BookingRepository bookingRepository;
    final TaxiRepository taxiRepository;

    public BookingService(BookingRepository bookingRepository, TaxiRepository taxiRepository) {
        this.bookingRepository = bookingRepository;
        this.taxiRepository = taxiRepository;
    }

    public Booking bookTaxi(TaxiBookingDTO taxiBookingDTO) {
        return taxiRepository.findById(taxiBookingDTO.getTaxiId())
                .map(t -> bookingRepository.save(new Booking(taxiBookingDTO.getCustomerId(), t)))
                .orElseThrow(() -> new RuntimeException("Unable to book taxi with id: "+taxiBookingDTO.getTaxiId()));
    }

    public boolean cancelBooking(long bookingId) {
        bookingRepository.deleteById(bookingId);
        return true;
    }
}
