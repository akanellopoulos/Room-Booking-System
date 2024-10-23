package com.example.room_booking_system.repository;

import com.example.room_booking_system.entity.RoomsBookings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
public interface RoomsBookingsRepository extends JpaRepository<RoomsBookings, Long> {
    @Query("SELECT r FROM RoomsBookings r WHERE r.roomId = :roomId AND  r.bookingDate = :bookingDate AND r.isDeleted = false ORDER BY r.timeFrom ASC")
    List<RoomsBookings> findAllByRoomIdAndBookingDate(Long roomId, LocalDate bookingDate);

    @Query("SELECT count(rb.roomId) FROM RoomsBookings rb WHERE rb.roomId = :roomId AND rb.email = :email AND rb.bookingDate = :bookingDate AND rb.isDeleted = false")
    Long findRoomBookingByRoomIdEmailAndDate(Optional<Long> roomId, String email, LocalDate bookingDate);

    @Query("SELECT rb FROM RoomsBookings rb WHERE rb.roomId = :roomId AND rb.bookingDate = :bookingDate AND rb.isDeleted = false " +
            "AND ((rb.timeFrom < :timeTo AND rb.timeTo > :timeFrom))")
    List<RoomsBookings> findConflictBookings(Optional<Long> roomId, LocalDate bookingDate, LocalTime timeFrom, LocalTime timeTo);

    @Query("SELECT rb FROM RoomsBookings rb WHERE rb.roomId = :roomId AND rb.email = :email AND rb.bookingDate = :bookingDate AND rb.isDeleted = false ")
    RoomsBookings findAllbyRoomIdEmailAndBookingDate(Optional<Long> roomId, String email, LocalDate bookingDate);

}
