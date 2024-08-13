package com.hhplus.concertReserv.exception;

import com.hhplus.concertReserv.domain.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException{
    private final String message;
    private final int status;
    private final String code;

    public UserNotFoundException(ErrorCode errorCode) {

      this.message = errorCode.getMessage();
      this.status = errorCode.getStatus();
      this.code = errorCode.getCode();
    }
}
