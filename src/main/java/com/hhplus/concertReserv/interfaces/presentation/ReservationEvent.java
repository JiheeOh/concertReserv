package com.hhplus.concertReserv.interfaces.presentation;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ReservationEvent {
    private UUID seatId;
    private UUID userId;
    private String confirmYn;
}
