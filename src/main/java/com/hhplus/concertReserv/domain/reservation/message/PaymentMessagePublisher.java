package com.hhplus.concertReserv.domain.reservation.message;

import com.hhplus.concertReserv.domain.reservation.event.PaymentEvent;

public interface PaymentMessagePublisher {
    void publish(PaymentEvent paymentEvent);
}
