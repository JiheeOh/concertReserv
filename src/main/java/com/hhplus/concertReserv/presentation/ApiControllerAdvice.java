package com.hhplus.concertReserv.presentation;

import com.hhplus.concertReserv.exception.OccupiedSeatException;
import com.hhplus.concertReserv.exception.TokenNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
class ApiControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = TokenNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTokenNotFoundException(Exception e) {
        return ResponseEntity.status(500).body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,e.toString()));
    }

    @ExceptionHandler(value = OccupiedSeatException.class)
    public ResponseEntity<ErrorResponse> handleOccupiedSeatException(Exception e) {
        return ResponseEntity.status(500).body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,e.toString()));
    }





}
