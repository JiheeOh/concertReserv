package com.hhplus.concertReserv.domain.reservation.message;

import com.hhplus.concertReserv.domain.reservation.event.ReservationEvent;

public interface ReservationMessagePublisher {

    void publish(ReservationEvent reservationMessage);
}
