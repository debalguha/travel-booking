package com.rhipe.marketting.flight.repository;

import com.rhipe.marketting.flight.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    Optional<Flight> findByName(String name);
    @Query("select f.name from Flight f")
    List<String> allFlightNames();
}
