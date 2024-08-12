package com.hhplus.concertReserv.interfaces.presentation;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class ReservationEvent {
    private UUID seatId;
    private UUID userId;
    private String confirmYn;
}
