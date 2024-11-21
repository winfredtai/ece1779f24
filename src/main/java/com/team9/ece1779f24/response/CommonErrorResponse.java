package com.team9.ece1779f24.response;

import com.team9.ece1779f24.enums.Status;
import lombok.Data;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

import java.util.List;

@Data
public class CommonErrorResponse {
    private final HttpStatusCode statusCode;
    private final String message;
    private final List<String> errors;
    public CommonErrorResponse(Status status) {
        this.statusCode = status.getHttpStatus();
        this.message = status.getMessage();
        this.errors = null;
    }
    public CommonErrorResponse(HttpStatusCode statusCode, String message, List<String> errors) {
        this.statusCode = statusCode;
        this.message = message;
        this.errors = errors;
    }
}