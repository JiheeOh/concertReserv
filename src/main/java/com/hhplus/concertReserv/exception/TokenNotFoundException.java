package com.hhplus.concertReserv.exception;

import com.hhplus.concertReserv.interfaces.presentation.ErrorCode;

public class TokenNotFoundException extends RuntimeException{
    private final ErrorCode ERROR_CODE;

    public TokenNotFoundException(ErrorCode errorCode) {
        ERROR_CODE = errorCode;
    }
}
