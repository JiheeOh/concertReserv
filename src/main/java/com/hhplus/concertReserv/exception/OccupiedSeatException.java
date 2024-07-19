package com.hhplus.concertReserv.exception;

import com.hhplus.concertReserv.interfaces.presentation.ErrorCode;

public class OccupiedSeatException extends RuntimeException{
    private final ErrorCode ERROR_CODE;

    public OccupiedSeatException( ErrorCode errorCode) {
        ERROR_CODE = errorCode;
    }
}
