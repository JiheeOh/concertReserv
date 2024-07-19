package com.hhplus.concertReserv.exception;

import com.hhplus.concertReserv.interfaces.presentation.ErrorCode;

public class UserNotFoundException extends RuntimeException{
    private final ErrorCode ERROR_CODE;

    public UserNotFoundException(ErrorCode errorCode) {
        ERROR_CODE = errorCode;
    }
}
