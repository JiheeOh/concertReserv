package com.hhplus.concertReserv.exception;

public class SeatNotFoundException extends RuntimeException{
    private final int ERROR_CODE;

    public SeatNotFoundException(String msg, int errorCode) {
        super(msg);
        ERROR_CODE = errorCode;
    }
}
