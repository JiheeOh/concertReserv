package com.hhplus.concertReserv.domain.sender;

import com.hhplus.concertReserv.domain.reservation.event.ReservationEvent;

public interface ReservationSender {
    void sendInfo(ReservationEvent reservationEvent);
}
