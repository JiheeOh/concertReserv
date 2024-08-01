package com.hhplus.concertReserv.interfaces.presentation;

import com.hhplus.concertReserv.exception.InvalidAmountException;
import com.hhplus.concertReserv.exception.OccupiedSeatException;
import com.hhplus.concertReserv.exception.TokenNotFoundException;
import com.hhplus.concertReserv.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // CustomException
    @ExceptionHandler(value = TokenNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTokenNotFoundException(TokenNotFoundException e) {
        log.error("handleTokenNotFoundException", e);
        return ResponseEntity.status(500).body(new ErrorResponse(e.getStatus(),e.getMessage()));
    }

    @ExceptionHandler(value = OccupiedSeatException.class)
    public ResponseEntity<ErrorResponse> handleOccupiedSeatException(OccupiedSeatException e) {
        log.error("handleOccupiedSeatException", e);
        return ResponseEntity.status(500).body(new ErrorResponse(e.getStatus(),e.getMessage()));
    }

    @ExceptionHandler(value = InvalidAmountException.class)
    public ResponseEntity<ErrorResponse> handleInvalidAmountException(InvalidAmountException e) {
        log.error("handleInvalidAmountException", e);
        return ResponseEntity.status(500).body(new ErrorResponse(e.getStatus(),e.getMessage()));
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
        log.error("handleUserNotFoundException", e);
        return ResponseEntity.status(500).body(new ErrorResponse(e.getStatus(),e.getMessage()));
    }


    //All Exception
    @ExceptionHandler(value = Exception.class)
    protected final ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex) {
        log.error("Exception", ex);
        return ResponseEntity.status(500).body(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR.getStatus(),ErrorCode.INTERNAL_SERVER_ERROR.getMessage()));
    }




}
