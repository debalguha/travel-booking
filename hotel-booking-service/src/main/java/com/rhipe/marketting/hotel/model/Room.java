package com.rhipe.marketting.hotel.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    @Id
    @GeneratedValue
    private Long id;

    private String roomNum;

    @ManyToOne
    @JoinColumn(name="hotel_id", nullable=false)
    private Hotel hotel;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return roomNum.equals(room.roomNum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNum);
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", roomNum='" + roomNum + '\'' +
                '}';
    }
}
