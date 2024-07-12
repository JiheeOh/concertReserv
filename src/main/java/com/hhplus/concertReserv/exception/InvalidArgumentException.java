package com.hhplus.concertReserv.exception;

public class InvalidArgumentException extends RuntimeException{
    private final int ERROR_CODE;

    public InvalidArgumentException(String msg, int errorCode) {
        super(msg);
        ERROR_CODE = errorCode;
    }
}
