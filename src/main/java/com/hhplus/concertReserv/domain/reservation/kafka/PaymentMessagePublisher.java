package com.hhplus.concertReserv.domain.reservation.kafka;

import com.hhplus.concertReserv.domain.reservation.event.PaymentEvent;

public interface PaymentMessagePublisher {
    void publish(PaymentEvent paymentEvent);
}
