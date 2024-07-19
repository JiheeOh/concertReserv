package com.hhplus.concertReserv.domain.concert;

import lombok.Getter;

@Getter
public enum SeatEnum {
    OCCUPIED("OCCUPIED"),
    RESERVED("RESERVED"),
    AVAILABLE("AVAILABLE");

    private final String status;

    SeatEnum(final String status ){
        this.status = status;

    }
}
