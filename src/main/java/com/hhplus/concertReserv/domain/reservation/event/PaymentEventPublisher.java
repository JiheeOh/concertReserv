package com.hhplus.concertReserv.domain.reservation.event;

public interface PaymentEventPublisher {

    void publish(PaymentEvent paymentEvent);
}
