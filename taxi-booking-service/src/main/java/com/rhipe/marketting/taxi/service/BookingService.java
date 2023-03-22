package com.rhipe.marketting.taxi.service;

import com.rhipe.marketting.taxi.model.Booking;
import com.rhipe.marketting.taxi.repository.BookingRepository;
import com.rhipe.marketting.taxi.repository.TaxiRepository;
import com.rhipe.marketting.taxi.rest.TaxiBookingDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

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

    public Optional<Booking> bookTaxi(TaxiBookingDTO taxiBookingDTO) {
        return taxiRepository.findById(taxiBookingDTO.taxiId())
                .map(t -> bookingRepository.save(new Booking(taxiBookingDTO.customerId(), t)));
    }

    public boolean compensate(long bookingId) {
        bookingRepository.deleteById(bookingId);
        return true;
    }
}
