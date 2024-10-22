package com.example.room_booking_system.repository;

import com.example.room_booking_system.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoomRepository
        extends JpaRepository<Room, Long> {
    @Query("SELECT s.id FROM Room s WHERE s.name = :name")
    Optional<Long> findIdByName(String name);
}
