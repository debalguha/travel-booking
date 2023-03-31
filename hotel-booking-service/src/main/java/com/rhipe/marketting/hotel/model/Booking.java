package com.rhipe.marketting.hotel.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Booking {
    @Id
    @GeneratedValue
    private Long id;

    private long customerId;

    @OneToMany
    private Set<Room> rooms;

    private String correlationId;

    public Booking(long customerId, Set<Room> rooms, String correlationId) {
        this.customerId = customerId;
        this.rooms = rooms;
        this.correlationId = correlationId;
    }
}
