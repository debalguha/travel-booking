package com.rhipe.marketting.taxi.service;

import com.rhipe.marketting.taxi.model.Booking;
import com.rhipe.marketting.taxi.repository.BookingRepository;
import com.rhipe.marketting.taxi.repository.TaxiRepository;
import com.rhipe.travel.booking.dto.TaxiBookingDTO;
import com.rhipe.travel.booking.dto.TaxiBookingResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@Slf4j
public class BookingService {
    final BookingRepository bookingRepository;
    final TaxiRepository taxiRepository;

    public BookingService(BookingRepository bookingRepository, TaxiRepository taxiRepository) {
        this.bookingRepository = bookingRepository;
        this.taxiRepository = taxiRepository;
    }

    public TaxiBookingResponseDTO bookTaxi(TaxiBookingDTO booking, String correlationId) {
        log.info("Booking {} on request {}", booking, correlationId);
        Booking taxiBooking = taxiRepository.findById(booking.taxiId())
                .map(t -> bookingRepository.save(new Booking(booking.customerId(), t, correlationId)))
                .orElseThrow(() -> new RuntimeException("Unable to book taxi with id: " + booking.taxiId()));
        return new TaxiBookingResponseDTO(taxiBooking.getCustomerId(), taxiBooking.getTaxi().getId(), taxiBooking.getCorrelationId());
    }

    public boolean cancelBooking(String correlationId) {
        bookingRepository.deleteByCorrelationId(correlationId);
        return true;
    }
}
