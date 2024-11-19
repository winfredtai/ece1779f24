package com.team9.ece1779f24.advice;

import com.team9.ece1779f24.enums.Status;
import com.team9.ece1779f24.exception.ApplicationException;
import com.team9.ece1779f24.response.CommonResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log4j2
public class ExceptionHandlers {
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<CommonResponse<?>> handleApplicationException(ApplicationException applicationException) {
        log.warn("Encountered an application exception. Message: {}",
                applicationException.getMessage(), applicationException);
        return ResponseEntity.status(applicationException.getStatus().getHttpStatus())
                .body(new CommonResponse<>(applicationException.getStatus()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse<?>> handleException(Exception exception) {
        log.warn("Encountered an unknown exception. Message: {}", exception.getMessage(), exception);
        return ResponseEntity.status(Status.UNKNOWN_ERROR.getHttpStatus())
                .body(new CommonResponse<>(Status.UNKNOWN_ERROR));
    }
}
