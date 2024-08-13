package com.hhplus.concertReserv.infrastructure.spring.reservation.event;

import com.hhplus.concertReserv.domain.reservation.event.ReservationEvent;
import com.hhplus.concertReserv.domain.reservation.event.ReservationEventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class ReservationSpringEventPublisher implements ReservationEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;


    public ReservationSpringEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publish(ReservationEvent reservationEvent){
        applicationEventPublisher.publishEvent(reservationEvent);
    }
}
