package com.example.room_booking_system.response;

public class RoomBookingResponse {
    private final boolean success;
    private final String message;

    public RoomBookingResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ValidationResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                '}';
    }
}
