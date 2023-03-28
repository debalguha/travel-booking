package com.rhipe.marketting.taxi.repository;

import com.rhipe.marketting.taxi.model.Taxi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaxiRepository extends JpaRepository<Taxi, Long> {
    @Query("select t.taxiId from Taxi t")
    List<String> allTaxiNums();
    Optional<Taxi> findTaxiByTaxiId(String taxiId);
}
