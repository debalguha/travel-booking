package com.rhipe.marketting.taxi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Booking {
    @Id
    @GeneratedValue
    private Long id;

    private long customerId;

    @OneToOne
    private Taxi taxi;

    public Booking(long customerId, Taxi taxi) {
        this.customerId = customerId;
        this.taxi = taxi;
    }
}
