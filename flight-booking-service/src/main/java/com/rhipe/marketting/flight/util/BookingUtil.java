package com.rhipe.marketting.flight.util;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class BookingUtil {
    public static <T> Set<T> unWrap(Set<Optional<T>> mayBeSeats) {
        return mayBeSeats
                .stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }
}
