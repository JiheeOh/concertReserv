package com.hhplus.concertReserv.exception;

public class TokenNotFoundException extends RuntimeException{
    private final int ERROR_CODE;

    public TokenNotFoundException(String msg, int errorCode) {
        super(msg);
        ERROR_CODE = errorCode;
    }
}
