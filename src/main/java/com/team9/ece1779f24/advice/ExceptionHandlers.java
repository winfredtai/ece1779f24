package com.team9.ece1779f24.advice;

import com.team9.ece1779f24.enums.Status;
import com.team9.ece1779f24.exception.ApplicationException;
import com.team9.ece1779f24.response.CommonResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.team9.ece1779f24.response.CommonErrorResponse;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
@Log4j2
public class ExceptionHandlers {
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<CommonErrorResponse> handleApplicationException(ApplicationException applicationException) {
        Status status = applicationException.getStatus();
        log.warn("Encountered an application exception. Message: {}", status.getMessage(), applicationException);
        CommonErrorResponse errorResponse = new CommonErrorResponse(
                status
        );
        return new ResponseEntity<>(errorResponse, status.getHttpStatus());
    }
    @ExceptionHandler(UndeclaredThrowableException.class)
    public ResponseEntity<CommonErrorResponse> handleUndeclaredThrowable(UndeclaredThrowableException ex) {
        Throwable cause = ex.getCause();
        if (cause instanceof ApplicationException) {
            return handleApplicationException((ApplicationException) cause);
        }
        log.error("Unhandled exception: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new CommonErrorResponse(Status.UNKNOWN_ERROR));
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CommonErrorResponse> handleValidationExceptions(ConstraintViolationException ex) {
        List<String> errors = ex.getConstraintViolations()
                .stream()
                .map(violation -> String.format("%s: %s",
                        violation.getPropertyPath(),
                        violation.getMessage()))
                .collect(Collectors.toList());

        CommonErrorResponse errorResponse = new CommonErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Validation failed",
                errors
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> String.format("%s: %s", error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());

        CommonErrorResponse errorResponse = new CommonErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Validation failed",
                errors
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonErrorResponse> handleException(Exception exception) {
        log.warn("Encountered an unknown exception. Message: {}", exception.getMessage(), exception);

        CommonErrorResponse errorResponse = new CommonErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                Status.UNKNOWN_ERROR.getMessage(),
                List.of(exception.getMessage())
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<CommonErrorResponse> handleNoResourceFoundException(NoResourceFoundException ex) {
        log.warn("Access No Resource end point: {}", ex.getMessage(), ex);
        CommonErrorResponse errorResponse = new CommonErrorResponse(
               Status.NORESOURCEFOUND
        );
        return new ResponseEntity<>(errorResponse, Status.NORESOURCEFOUND.getHttpStatus());
    }
}
