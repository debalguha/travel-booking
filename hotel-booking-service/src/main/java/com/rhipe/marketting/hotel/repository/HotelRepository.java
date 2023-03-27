package com.rhipe.marketting.hotel.repository;

import com.rhipe.marketting.hotel.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
     Optional<Hotel> findByHotelName(String name);
     @Query("select h.hotelName from Hotel h")
     List<String> allHotelNames();
}
