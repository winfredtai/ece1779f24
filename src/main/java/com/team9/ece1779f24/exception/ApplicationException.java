package com.team9.ece1779f24.exception;

import com.team9.ece1779f24.enums.Status;

public class ApplicationException extends Exception {
    private Status status;
    public ApplicationException(Status status) {
        super(status.getMessage());
        this.status = status;
    }
    public Status getStatus() {
        return status;
    }
}
