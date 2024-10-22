package com.example.room_booking_system.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table
public class RoomsBookings {
    @Id
    @SequenceGenerator(

            name = "rooms_bookings_sequence",
            sequenceName = "rooms_bookings_sequence",
            allocationSize = 1
    )

    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "rooms_bookings_sequence")
    private Long id;
    private Long roomId;
    private String email;
    private LocalDate bookingDate;
    private LocalTime timeFrom;
    private LocalTime timeTo;

    public RoomsBookings() {
    }

    public RoomsBookings(Long roomId, String email, LocalDate bookingDate, LocalTime timeFrom, LocalTime timeTo) {
        this.roomId = roomId;
        this.email = email;
        this.bookingDate = bookingDate;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public LocalTime getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(LocalTime timeFrom) {
        this.timeFrom = timeFrom;
    }

    public LocalTime getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(LocalTime timeTo) {
        this.timeTo = timeTo;
    }
}
