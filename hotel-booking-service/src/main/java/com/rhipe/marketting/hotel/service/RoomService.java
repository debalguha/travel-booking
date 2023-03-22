package com.rhipe.marketting.hotel.service;

import com.rhipe.marketting.hotel.model.Room;
import com.rhipe.marketting.hotel.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService {
    final RoomRepository roomRepository;

    public Optional<Room> lookupSeat(long roomId) {
        return roomRepository.findById(roomId);
    }
}
