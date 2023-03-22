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
    private long id;

    private long customerId;

    @OneToMany
    private Set<Room> rooms;

    public Booking(long customerId, Set<Room> rooms) {
        this.customerId = customerId;
        this.rooms = rooms;
    }
}
