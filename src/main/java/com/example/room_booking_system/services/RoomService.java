package com.example.room_booking_system.services;

import com.example.room_booking_system.entity.RoomsBookings;
import com.example.room_booking_system.repository.RoomRepository;
import com.example.room_booking_system.repository.RoomsBookingsRepository;
import com.example.room_booking_system.response.RoomBookingResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@Slf4j
public class RoomService {
    private final RoomRepository roomRepository;
    private final RoomsBookingsRepository roomsBookingsRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository, RoomsBookingsRepository roomsBookingsRepository) {
        this.roomRepository = roomRepository;
        this.roomsBookingsRepository = roomsBookingsRepository;
    }

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);


    /**
     * Return a list with records from the table ROOMSBOOKINGS.
     *
     * @param name
     * @param date
     * @return A list containing the results of the operation
     */
    public List<RoomsBookings> getRoomBookings(String name, LocalDate date) {

        // Return roomId according to the room name
        Optional<Long> room = roomRepository.findIdByName(name);

        log.info("Request getRoomBookings with params{ name: " + name + ", date: " + date + " }");

        // In case the room name does not exist in our DB, the method returns an empty ArrayList
        return room.map(r -> roomsBookingsRepository.findAllByRoomIdAndBookingDate(r, date)).orElseGet(ArrayList::new);
    }


    /**
     * Add a new booking for a room
     *
     * @param name
     * @param email
     * @param date
     * @param timeFrom
     * @param timeTo
     * @return A RoomBookingResponse object containing the result of the operation
     */
    public RoomBookingResponse addNewBooking(String name, String email, LocalDate date, LocalTime timeFrom, LocalTime timeTo) {

        log.info("Request addNewBooking with params{ name: " + name + ", email: " + email + ", date: " + date + ", timeFrom: " + timeFrom + ", timeTo: " + timeTo + " }");

        // Validate params: must not be null
        if (name == null || email == null || date == null || timeFrom == null || timeTo == null) {
            log.info("Invalid null parameters");
            return new RoomBookingResponse(false, "INVALID_NULL_PARAMETERS");
        }

        // Validate date: must be now or in the future
        if (date.isBefore(LocalDate.now()) || (date.isEqual(LocalDate.now()) && timeFrom.isBefore(LocalTime.now()))) {
            log.info("The booking date cannot be in the past, given date: {} - now: {} for booking with email: {},and timeFrom: {} - time now: {}", date, LocalDate.now(), email, timeFrom, LocalTime.now());
            return new RoomBookingResponse(false, "INVALID_OLD_DATE");
        }

        // Validate email
        if (!isValidEmail(email)) {
            log.info("Invalid email format: " + email);
            return new RoomBookingResponse(false, "INVALID_EMAIL_FORMAT");
        }

        // Validate timeFrom and timeTo
        if (!isValidTimeSlot(timeFrom, timeTo)) {
            log.info("Invalid time slot. The booking for the email: {} should be exactly 1, 2, or 3... hours long, and times must be on the hour.", email);
            return new RoomBookingResponse(false, "INVALID_TIME_SLOT");
        }

        // Return roomId according to the room name
        Optional<Long> room = roomRepository.findIdByName(name);

        // Validate room name: must be one of the recommended
        if (!room.isPresent()) {
            log.info("Invalid parameter name: {} for the booking with email: {}", name, email);
            return new RoomBookingResponse(false, "INVALID_PARAMETER_NAME");
        }

        // Return if the email has booked for the same room on the same day
        Long countBookingByRoomIdEmailAndDate = roomsBookingsRepository.findRoomBookingByRoomIdEmailAndDate(room, email, date);

        // Validate if the email is eligible to book
        if (countBookingByRoomIdEmailAndDate > 0) {
            log.info("Invalid, the email: {} has already booked the room: {} for the date: {}", email, name, date);
            return new RoomBookingResponse(false, "INVALID_EMAIL_HAS_ALREADY_BOOKED_FOR_THIS_DATE");
        }

        // Return if there is conflict about the booking periods
        List<RoomsBookings> conflictBookings = roomsBookingsRepository.findConflictBookings(room, date, timeFrom, timeTo);

        //Validate if the timeFrom and timeTo are already in use
        if (!conflictBookings.isEmpty()) {
            log.info("Invalid booking period for the booking with email: {}, please choose another time", email);
            return new RoomBookingResponse(false, "INVALID_BOOKING_PERIOD");
        }

        // Save the new booking if validations pass
        RoomsBookings booking = new RoomsBookings(room.get(), email, date, timeFrom, timeTo);

        //Insert a new row in the table: ROOMSBOOKINGS
        roomsBookingsRepository.save(booking);

        log.info("Success, the email: {} booked the room: {} for the date: {} on {} - {}", email, name, date, timeFrom, timeTo);
        return new RoomBookingResponse(true, "SUCCESS_BOOKING_CREATED");

    }

    // Validate email using regex
    private boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    // Validate that the time slot is exactly 1, 2, or 3 hours and the times are full hours (e.g., 13:00)
    private boolean isValidTimeSlot(LocalTime timeFrom, LocalTime timeTo) {

        // Calculate the duration between timeFrom and timeTo
        long hoursBetween = Duration.between(timeFrom, timeTo).toHours();

        // Check if the duration is 1, 2, or 3... hours
        return hoursBetween > 0 && hoursBetween % 1 == 0;
    }


    /**
     * Delete a booking
     *
     * @param name
     * @param email
     * @param date
     * @return A RoomBookingResponse object containing the result of the operation, isSuccess: true / false and a message ( the messages and the end-points are described on the readme.md )
     */
    public RoomBookingResponse removeBooking(String name, String email, LocalDate date) {

        // Validate params: must not be null
        if (name == null || email == null || date == null) {
            log.info("Invalid null parameters");
            return new RoomBookingResponse(false, "INVALID_NULL_PARAMETERS");
        }

        // Return roomId according to the room name
        Optional<Long> room = roomRepository.findIdByName(name);

        // Validate room name: must be one of the recommended
        if (!room.isPresent()) {
            log.info("Invalid parameter name: {} for the booking with email: {}", name, email);
            return new RoomBookingResponse(false, "INVALID_PARAMETER_NAME");
        }

        //Return the record for the requested cancellation booking
        RoomsBookings roomBooking = roomsBookingsRepository.findAllbyRoomIdEmailAndBookingDate(room, email, date);

        // Validate if the requested cancellation booking exist
        if (roomBooking == null) {
            log.info("Invalid, the email: {} has not booked the room: {} for the date: {}", email, name, date);
            return new RoomBookingResponse(false, "INVALID_BOOKING_DOES_NOT_EXIST");
        }

        // Validate date: must be now or in the future
        if (date.isBefore(LocalDate.now()) || (date.isEqual(LocalDate.now()) && roomBooking.getTimeFrom().isBefore(LocalTime.now()))) {
            log.info("The booking date cannot be in the past, given date: {} - now: {} for booking with email: {},and timeFrom: {} - time now: {}", date, LocalDate.now(), email, roomBooking.getTimeFrom(), LocalTime.now());
            return new RoomBookingResponse(false, "INVALID_OLD_DATE");
        }

        roomBooking.setDeleted(true);
        roomsBookingsRepository.save(roomBooking);

        log.info("Success, the requested booking with details name: {}, email: {}, date: {}, timeFrom: {},and timeTo: {} has been removed, isDeleted flag changed to isDeleted: {}", name, email, date, roomBooking.getTimeFrom(), roomBooking.getTimeTo(), roomBooking.isDeleted());
        return new RoomBookingResponse(true, "SUCCESS_BOOKING_DELETED");
    }

}
