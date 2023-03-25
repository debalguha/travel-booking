package com.rhipe.marketting.hotel.service;

import com.rhipe.marketting.hotel.model.Booking;
import com.rhipe.marketting.hotel.model.Room;
import com.rhipe.marketting.hotel.repository.BookingRepository;
import com.rhipe.marketting.hotel.repository.RoomRepository;
import com.rhipe.travel.booking.dto.RoomBookingDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.rhipe.marketting.hotel.util.BookingUtil.unWrap;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingService {
    final BookingRepository bookingRepository;
    final RoomRepository roomRepository;


    public Booking bookRoom(RoomBookingDTO roomBookingDTO) {
        Set<Optional<Room>> rooms = roomBookingDTO.getRoomId()
                .stream()
                .map(roomRepository::findById)
                .collect(Collectors.toSet());
        return bookingRepository.save(new Booking(roomBookingDTO.getCustomerId(), unWrap(rooms)));
    }

    public boolean cancelBooking(long bookingId) {
        bookingRepository.deleteById(bookingId);
        return true;
    }
}
