package com.rhipe.marketting.taxi.service;

import com.rhipe.marketting.taxi.model.Taxi;
import com.rhipe.marketting.taxi.repository.TaxiRepository;
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

    final TaxiRepository taxiRepository;
    @Override
    public void run(String... args) throws Exception {
        final EasyRandom easyRandom = new EasyRandom(
                new EasyRandomParameters().excludeField(
                        FieldPredicates.named("id")
                                .and(FieldPredicates.ofType(Long.class))
                                .and(FieldPredicates.inClass(Taxi.class))
                )
        );
        List<Taxi> flights = IntStream.range(0, 10)
                .mapToObj(i -> easyRandom.nextObject(Taxi.class))
                .map(taxi -> taxiRepository.save(taxi))
                .collect(Collectors.toList());
        log.info("Flights created {}"+flights.size());
    }
}
