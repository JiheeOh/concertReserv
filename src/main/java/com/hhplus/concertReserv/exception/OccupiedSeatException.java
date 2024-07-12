package com.hhplus.concertReserv.exception;

public class OccupiedSeatException extends RuntimeException{
    private final int ERROR_CODE;

    public OccupiedSeatException(String msg, int errorCode) {
        super(msg);
        ERROR_CODE = errorCode;
    }
}
