package com.example.room_booking_system.config;

import com.example.room_booking_system.entity.Room;
import com.example.room_booking_system.entity.RoomsBookings;

import com.example.room_booking_system.repository.RoomRepository;
import com.example.room_booking_system.repository.RoomsBookingsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;


@Configuration
public class Config {

    @Bean
    CommandLineRunner commandLineRunner(RoomRepository repo1, RoomsBookingsRepository repo2) {
        return args -> {

            // Insert dummy data on the room table
            Room KALAMATA = new Room(
                    1L,
                    "KALAMATA"
            );

            Room VOLOS = new Room(
                    2L,
                    "VOLOS"
            );
            Room PIRAEUS = new Room(
                    3L,
                    "PIRAEUS"
            );
            Room PATRA = new Room(
                    4L,
                    "PATRA"
            );
            Room RODOS = new Room(
                    5L,
                    "RODOS"
            );

            repo1.saveAll(
                    List.of(KALAMATA, VOLOS, PIRAEUS, PATRA, RODOS)
            );


            // Insert dummy data on the roomsbookings table
            RoomsBookings a1 = new RoomsBookings(
                    1L,
                    "a1@gmail.com",
                    LocalDate.of(2024, Month.OCTOBER, 20),
                    LocalTime.of(13, 0),
                    LocalTime.of(14, 0)
            );
            RoomsBookings a2 = new RoomsBookings(
                    1L,
                    "a2@gmail.com",
                    LocalDate.of(2024, Month.OCTOBER, 20),
                    LocalTime.of(14, 0),
                    LocalTime.of(15, 0)
            );
            RoomsBookings a3 = new RoomsBookings(
                    1L,
                    "a3@gmail.com",
                    LocalDate.of(2024, Month.OCTOBER, 22),
                    LocalTime.of(15, 0),
                    LocalTime.of(16, 0)
            );
            RoomsBookings a4 = new RoomsBookings(
                    1L,
                    "a4@gmail.com",
                    LocalDate.of(2024, Month.OCTOBER, 22),
                    LocalTime.of(16, 0),
                    LocalTime.of(17, 0)
            );
            RoomsBookings a5 = new RoomsBookings(
                    1L,
                    "a5@gmail.com",
                    LocalDate.of(2024, Month.OCTOBER, 23),
                    LocalTime.of(17, 0),
                    LocalTime.of(18, 0)
            );
            RoomsBookings b1 = new RoomsBookings(
                    2L,
                    "b1@gmail.com",
                    LocalDate.of(2024, Month.OCTOBER, 20),
                    LocalTime.of(12, 0),
                    LocalTime.of(13, 0)
            );
            RoomsBookings b2 = new RoomsBookings(
                    2L,
                    "b2@gmail.com",
                    LocalDate.of(2024, Month.OCTOBER, 22),
                    LocalTime.of(13, 0),
                    LocalTime.of(14, 0)
            );
            RoomsBookings b3 = new RoomsBookings(
                    2L,
                    "b3@gmail.com",
                    LocalDate.of(2024, Month.OCTOBER, 23),
                    LocalTime.of(16, 0),
                    LocalTime.of(17, 0)
            );
            RoomsBookings c1 = new RoomsBookings(
                    3L,
                    "c1@gmail.com",
                    LocalDate.of(2024, Month.OCTOBER, 22),
                    LocalTime.of(13, 0),
                    LocalTime.of(14, 0)
            );
            RoomsBookings c2 = new RoomsBookings(
                    3L,
                    "c2@gmail.com",
                    LocalDate.of(2024, Month.OCTOBER, 22),
                    LocalTime.of(14, 0),
                    LocalTime.of(15, 0)
            );
            RoomsBookings c3 = new RoomsBookings(
                    3L,
                    "c3@gmail.com",
                    LocalDate.of(2024, Month.OCTOBER, 23),
                    LocalTime.of(15, 0),
                    LocalTime.of(16, 0)
            );

            repo2.saveAll(List.of(a1, a2, a3, a4, a5, b1, b2, b3, c1, c2, c3));

        };
    }
}
