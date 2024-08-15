package com.hhplus.concertReserv.domain.reservation.event;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationEvent {
    private UUID seatId;
    private UUID userId;
    private String confirmYn;
}
