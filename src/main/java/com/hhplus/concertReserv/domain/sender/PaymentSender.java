package com.hhplus.concertReserv.domain.sender;

import com.hhplus.concertReserv.domain.reservation.event.PaymentEvent;

public interface PaymentSender {
    void sendInfo(PaymentEvent paymentEvent);
}
