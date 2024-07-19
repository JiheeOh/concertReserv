package com.hhplus.concertReserv.exception;

import com.hhplus.concertReserv.interfaces.presentation.ErrorCode;

public class InvalidAmountException extends RuntimeException{
    private final ErrorCode ERROR_CODE;

    public InvalidAmountException(ErrorCode errorCode) {
        ERROR_CODE = errorCode;
    }
}
