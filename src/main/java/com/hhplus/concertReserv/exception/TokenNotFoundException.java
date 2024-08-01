package com.hhplus.concertReserv.exception;

import com.hhplus.concertReserv.interfaces.presentation.ErrorCode;
import lombok.Getter;

@Getter
public class TokenNotFoundException extends RuntimeException {
    private final String message;
    private final int status;
    private final String code;

    public TokenNotFoundException(ErrorCode errorCode) {
        this.message = errorCode.getMessage();
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
    }
}
