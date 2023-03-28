package com.rhipe.marketting.taxi.service;

import com.rhipe.marketting.taxi.model.Taxi;
import com.rhipe.marketting.taxi.repository.TaxiRepository;
import com.rhipe.travel.booking.dto.TaxiResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaxiService {
    final TaxiRepository taxiRepository;

    public TaxiResponseDTO lookupTaxi(String taxiId) {
        return taxiRepository.findTaxiByTaxiId(taxiId)
                .map(t -> new TaxiResponseDTO(t.getId(), t.getTaxiId()))
                .orElseThrow(() -> new RuntimeException("Unable to locate taxi by %s".formatted(taxiId)));
    }
    public List<String> allTaxiNums() {
        return taxiRepository.allTaxiNums();
    }

}
