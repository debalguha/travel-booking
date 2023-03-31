package com.rhipe.marketting.hotel.service;

import com.rhipe.marketting.hotel.model.Booking;
import com.rhipe.marketting.hotel.model.Room;
import com.rhipe.marketting.hotel.repository.BookingRepository;
import com.rhipe.marketting.hotel.repository.RoomRepository;
import com.rhipe.travel.booking.dto.RoomBookingDTO;
import com.rhipe.travel.booking.dto.RoomBookingResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.rhipe.marketting.hotel.util.BookingUtil.unWrap;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BookingService {
    final BookingRepository bookingRepository;
    final RoomRepository roomRepository;


    public RoomBookingResponseDTO bookRoom(RoomBookingDTO booking, String correlationId) {
        log.info("Booking {} on request {}", booking, correlationId);
        Set<Optional<Room>> rooms = booking.roomId()
                .stream()
                .map(roomRepository::findById)
                .collect(Collectors.toSet());
        Booking roomBooking = bookingRepository.save(new Booking(booking.customerId(), unWrap(rooms), correlationId));
        return new RoomBookingResponseDTO(roomBooking.getCustomerId(), roomBooking.getRooms().stream().map(Room::getId).collect(Collectors.toSet()), roomBooking.getCorrelationId());
    }

    public boolean cancelBooking(String correlationId) {
        bookingRepository.deleteByCorrelationId(correlationId);
        return true;
    }
}
