package com.rhipe.marketting.taxi.service;

import com.rhipe.marketting.taxi.model.Taxi;
import com.rhipe.marketting.taxi.repository.TaxiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaxiService {
    final TaxiRepository taxiRepository;

    public Optional<Taxi> lookupSeat(long taxiId) {
        return taxiRepository.findById(taxiId);
    }
}
