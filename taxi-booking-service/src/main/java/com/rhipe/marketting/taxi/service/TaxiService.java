package com.rhipe.marketting.taxi.service;

import com.rhipe.marketting.taxi.model.Taxi;
import com.rhipe.marketting.taxi.repository.TaxiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaxiService {
    final TaxiRepository taxiRepository;

    public Optional<Taxi> lookupTaxi(long taxiId) {
        return taxiRepository.findById(taxiId);
    }
    public List<String> allTaxiNums() {
        return taxiRepository.allTaxiNums();
    }

}
