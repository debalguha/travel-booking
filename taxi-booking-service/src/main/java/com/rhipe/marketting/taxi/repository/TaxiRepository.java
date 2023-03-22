package com.rhipe.marketting.taxi.repository;

import com.rhipe.marketting.taxi.model.Taxi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxiRepository extends JpaRepository<Taxi, Long> {
}
