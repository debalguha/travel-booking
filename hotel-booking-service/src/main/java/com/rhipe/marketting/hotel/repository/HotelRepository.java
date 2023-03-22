package com.rhipe.marketting.hotel.repository;

import com.rhipe.marketting.hotel.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
}
