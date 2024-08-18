package com.hhplus.concertReserv.domain.reservation.message;

import lombok.Getter;

@Getter
public enum OutboxEnum {

    INIT("INIT"),
    PUBLISHED("PUBLISHED"),
    COMPLETED("COMPLETED");

    private final String status;

    OutboxEnum(final String status ){
        this.status = status;

    }
}
