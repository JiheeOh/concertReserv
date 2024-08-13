package com.hhplus.concertReserv.infrastructure.spring.reservation.event;

import com.hhplus.concertReserv.domain.reservation.event.PaymentEvent;
import com.hhplus.concertReserv.domain.reservation.event.PaymentEventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class PaymentSpringEventPublisher implements PaymentEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;


    public PaymentSpringEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publish(PaymentEvent paymentEvent){
        applicationEventPublisher.publishEvent(paymentEvent);
    }
}
