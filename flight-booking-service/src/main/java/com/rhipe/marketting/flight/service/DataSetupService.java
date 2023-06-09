package com.rhipe.marketting.flight.service;

import com.rhipe.marketting.flight.model.Flight;
import com.rhipe.marketting.flight.model.Seat;
import com.rhipe.marketting.flight.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.FieldPredicates;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataSetupService implements CommandLineRunner {

    final FlightRepository flightRepository;

    @Override
    public void run(String... args) {
        final EasyRandom easyRandom = new EasyRandom(
                new EasyRandomParameters()
                        .randomizationDepth(3)
                        .excludeField(
                                FieldPredicates.named("id")
                                        .and(FieldPredicates.ofType(Long.class))
                                        .and(FieldPredicates.inClass(Flight.class))
                        ).excludeField(FieldPredicates.named("id")
                                .and(FieldPredicates.ofType(Long.class))
                                .and(FieldPredicates.inClass(Seat.class))
                        ).excludeField(FieldPredicates.named("flight")
                                .and(FieldPredicates.ofType(Flight.class))
                                .and(FieldPredicates.inClass(Seat.class))
                        )
        );
        List<Flight> flights = IntStream.range(0, 10)
                .mapToObj(i -> easyRandom.nextObject(Flight.class))
                .map(f -> {
                    f.getSeats().forEach(s -> s.setFlight(f));
                    return f;
                })
                .peek(f -> log.info(f.toString()))
                .map(flightRepository::save)
                .toList();
        log.info("Flights created {}", flights.size());
    }
}
