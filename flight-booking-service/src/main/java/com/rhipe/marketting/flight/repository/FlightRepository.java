package com.rhipe.marketting.flight.repository;

import com.rhipe.marketting.flight.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    Optional<Flight> findByName(String name);
}
