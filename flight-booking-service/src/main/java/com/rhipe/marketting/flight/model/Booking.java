package com.rhipe.marketting.flight.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Booking {
    @Id
    @GeneratedValue
    private long id;

    private long customerId;

    @OneToMany
    private Set<Seat> seats;

    public Booking(long customerId, Set<Seat> seats) {
        this.customerId = customerId;
        this.seats = seats;
    }
}
