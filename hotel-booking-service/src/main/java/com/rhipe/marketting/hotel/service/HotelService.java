package com.rhipe.marketting.hotel.service;

import com.rhipe.marketting.hotel.repository.HotelRepository;
import com.rhipe.travel.booking.dto.HotelResponseDTO;
import com.rhipe.travel.booking.dto.RoomResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class HotelService {
    final HotelRepository hotelRepository;

    public HotelResponseDTO lookupHotel(String hotelName) {
        return hotelRepository.findByHotelName(hotelName)
                .map(h -> new HotelResponseDTO(h.getId(), h.getHotelName(),
                                h.getRooms()
                                        .stream()
                                        .map(r -> new RoomResponseDTO(r.getId(), r.getRoomNum()))
                                        .collect(Collectors.toSet())
                        )
                )
                .orElseThrow(() -> new RuntimeException("Can't lookup hotel with name %s".formatted(hotelName)));
    }
}
