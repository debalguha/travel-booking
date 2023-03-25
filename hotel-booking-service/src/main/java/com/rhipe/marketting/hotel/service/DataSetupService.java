package com.rhipe.marketting.hotel.service;

import com.rhipe.marketting.hotel.model.Hotel;
import com.rhipe.marketting.hotel.model.Room;
import com.rhipe.marketting.hotel.repository.HotelRepository;
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

    final HotelRepository hotelRepository;

    @Override
    public void run(String... args) throws Exception {
        final EasyRandom easyRandom = new EasyRandom(
                new EasyRandomParameters()
                        .excludeField(FieldPredicates.named("id")
                                        .and(FieldPredicates.ofType(Long.class))
                                        .and(FieldPredicates.inClass(Hotel.class))
                        ).excludeField(FieldPredicates.named("id")
                                .and(FieldPredicates.ofType(Long.class))
                                .and(FieldPredicates.inClass(Room.class))
                        ).excludeField(FieldPredicates.named("hotel")
                                .and(FieldPredicates.ofType(Hotel.class))
                                .and(FieldPredicates.inClass(Room.class))
                        )
        );
        List<Hotel> hotels = IntStream.range(0, 10)
                .mapToObj(i -> easyRandom.nextObject(Hotel.class))
                .peek(h -> log.info(h.toString()))
                .map(h -> {
                    h.getRooms().forEach(s -> s.setHotel(h));
                    return h;
                })
                .map(hotelRepository::save)
                .toList();
        log.info("Hotels created {}", hotels.size());
    }
}
