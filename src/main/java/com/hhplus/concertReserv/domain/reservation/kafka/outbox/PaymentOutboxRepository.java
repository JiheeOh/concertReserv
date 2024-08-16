package com.hhplus.concertReserv.domain.reservation.kafka.outbox;

import com.hhplus.concertReserv.domain.reservation.kafka.outbox.entity.PaymentOutbox;

import java.util.UUID;

public interface PaymentOutboxRepository {
    void save(PaymentOutbox outbox);

    void complete(PaymentOutbox outbox);

    PaymentOutbox findByPaymentId(UUID paymentId);
}
