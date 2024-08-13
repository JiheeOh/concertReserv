package com.hhplus.concertReserv.domain.reservation.event;

public interface ReservationEventPublisher {
    void publish(ReservationEvent reservationEvent);
}
