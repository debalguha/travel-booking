package com.rhipe.marketting.flight.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seat {
    @Id
    @GeneratedValue
    private Long id;

    private String seatIdentifier;

    @ManyToOne
    @JoinColumn(name="flight_id", nullable=false)
    private Flight flight;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return seatIdentifier.equals(seat.seatIdentifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seatIdentifier);
    }

    @Override
    public String toString() {
        return "Seat{" +
                "id=" + id +
                ", seatIdentifier='" + seatIdentifier + '\'' +
                '}';
    }
}
