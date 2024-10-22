package com.example.room_booking_system.controllers;


import com.example.room_booking_system.entity.RoomsBookings;
import com.example.room_booking_system.response.RoomBookingResponse;
import com.example.room_booking_system.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(path = "/room")
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    /**
     * Return a list with records from the table ROOMSBOOKINGS.
     *
     * @param name
     * @param date
     * @return A list containing the results of the operation
     */
    @GetMapping
    public ResponseEntity<List<RoomsBookings>> retrieveBookingByRoomAndDay(@RequestParam String name,
                                                                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {

        List<RoomsBookings> rb = roomService.getRoomBookings(name, date);


        return ResponseEntity.ok(rb);
    }


    /**
     * Add a new booking
     *
     * @param name
     * @param email
     * @param date
     * @param timeFrom
     * @param timeTo
     * @return A RoomBookingResponse object containing the result of the operation, isSuccess: true / false and a message ( the messages and the end-points are described on the HELP.md )
     */
    @PostMapping
    public RoomBookingResponse addRoomBooking(@RequestParam String name,
                                              @RequestParam String email,
                                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime timeFrom,
                                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime timeTo) {


        RoomBookingResponse result = roomService.addNewBooking(name, email, date, timeFrom, timeTo);


        return new RoomBookingResponse(result.isSuccess(), result.getMessage());


    }

    /**
     * Delete a booking
     *
     * @param name
     * @param email
     * @param date
     * @return A RoomBookingResponse object containing the result of the operation, isSuccess: true / false and a message ( the messages and the end-points are described on the HELP.md )
     */
    @DeleteMapping
    public RoomBookingResponse deleteBooking(@RequestParam String name,
                                             @RequestParam String email,
                                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        RoomBookingResponse result = roomService.removeBooking(name, email, date);

        return new RoomBookingResponse(result.isSuccess(), result.getMessage());

    }

}
