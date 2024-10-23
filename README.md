
### Implementation
I created two DBs: room, roomsbookings.

room:
* id (Long)
* name(String)

roomsbookings:
* id (Long)
* roomId (Long)
* email (String)
* date (LocalDate)
* timeFrom (LocalTime)
* TimeTo (LocalTime)
* is_deleted (boolean)

Also, dummy data are created on the DB. Please check the java file in the path: com/example/room_booking_system/config/Config.java.

Three end-points are created with base path: /room.

The end-points are the following:

1. A GET end-point that accepts two mandatory values:

* Long name
* String email

The end-point returns a list with the records from the roomsbookins table.

For instance, a URL for a GET request: http://localhost:8080/room?name=VOLOS&date=2024-10-22

A response example:
{
"id": 1,
"roomId": 1,
"email": "a1@gmail.com",
"bookingDate": "2024-10-23",
"timeFrom": "13:00:00",
"timeTo": "14:00:00",
"deleted": false
},
{
"id": 2,
"roomId": 1,
"email": "a2@gmail.com",
"bookingDate": "2024-10-23",
"timeFrom": "14:00:00",
"timeTo": "15:00:00",
"deleted": false
}

2. A POST end-point that accepts five mandatory values:

* String name
* String email
* LocalDate date
* LocalTime timeFrom
* LocalTime timeTo

The end-point returns (after validations) a RoomBookingResponse object containing the result of the operation, isSuccess: true / false and a message.
The response messages are the below:

* INVALID_NULL_PARAMETERS: Invalid null parameters

* INVALID_OLD_DATE: The booking date cannot be in the past

* INVALID_EMAIL_FORMAT: Invalid email format

* INVALID_TIME_SLOT: Invalid time slot. The booking should be exactly 1, 2, or 3... hours long, and times must be on the hour.

* INVALID_PARAMETER_NAME: Invalid parameter room name

* INVALID_BOOKING_PERIOD: Invalid booking period, please choose another time

* INVALID_EMAIL_HAS_ALREADY_BOOKED_FOR_THIS_DATE: Invalid the email has already booked for this date

* SUCCESS_BOOKING_CREATED: Booking created successfully

For instance, a URL for a POST request: http://localhost:8080/room?name=VOLOS&email=a3@gmail.com&date=2024-10-22&timeFrom=21:00:00&timeTo=22:00:00

A response example:
{
"success": false,
"message": "INVALID_BOOKING_PERIOD"
}

3. A DELETE end-point that accepts three mandatory values:

* String name
* String email
* LocalDate date

We do not remove the appropriate record from the table roomsbookings, but we update the column "isDeleted" from "false" to "true". So, we will keep a history of cancellations.

The end-point returns (after validations) a RoomBookingResponse object containing the result of the operation, isSuccess: true / false and a message.
The response messages are the below:

* INVALID_NULL_PARAMETERS: Invalid null parameters

* INVALID_PARAMETER_NAME: Invalid parameter room name

* INVALID_BOOKING_DOES_NOT_EXIST: Invalid the email has not booked the requested room and date

* INVALID_OLD_DATE: Invalid the requested cancellation booking has an old date or time

* SUCCESS_BOOKING_DELETED: The requested cancellation booking is deleted

For instance, a URL for a DELETE request: http://localhost:8080/room?name=RODOS&email=a3@gmail.com&date=2024-10-22
A response example:
{
"success": true,
"message": "SUCCESS_BOOKING_DELETED"
}