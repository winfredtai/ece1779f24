package com.team9.ece1779f24.enums;

import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public enum Status {
    OK(1000, "Successful", HttpStatus.OK),
    NOTICKETFOUND(4004, "No Ticket is found", HttpStatus.OK),
    MISSING_FIELD(4005, "Missing or Invalid required field(s)", HttpStatus.BAD_REQUEST),
    TICKETCREATED(1001, "Ticket created", HttpStatus.OK),
    BOOKINGID_DUPLICATION(4006,"Duplicate Booking ID" , HttpStatus.BAD_REQUEST ),
    TICKETNUMBER_DUPLICATION(4007,"Duplicate Ticket Number" , HttpStatus.BAD_REQUEST ),
    UNKNOWN_ERROR(9999, "Unknown error", HttpStatus.INTERNAL_SERVER_ERROR);
    ;
    private final int code;
    private final String message;
    private final HttpStatus httpStatus;
    Status(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
