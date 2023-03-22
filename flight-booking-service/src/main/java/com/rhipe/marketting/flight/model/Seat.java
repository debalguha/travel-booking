package com.rhipe.marketting.flight.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seat {
    @Id
    @GeneratedValue
    private long id;

    private String seatIdentifier;

    @ManyToOne
    @JoinColumn(name="hotel_id", nullable=false)
    private Flight flight;

}
