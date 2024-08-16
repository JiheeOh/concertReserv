package com.hhplus.concertReserv.exception;

import com.hhplus.concertReserv.domain.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class NotFoundOutboxException extends RuntimeException {
    private final int status;

    private final String code;

    private final String message;

    public NotFoundOutboxException(ErrorCode errorCode) {
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }
}
