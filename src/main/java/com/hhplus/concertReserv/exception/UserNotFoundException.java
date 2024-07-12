package com.hhplus.concertReserv.exception;

public class UserNotFoundException extends RuntimeException{
    private final int ERROR_CODE;

    public UserNotFoundException(String msg, int errorCode) {
        super(msg);
        ERROR_CODE = errorCode;
    }
}
