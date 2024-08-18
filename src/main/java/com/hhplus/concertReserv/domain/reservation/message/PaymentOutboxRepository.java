package com.hhplus.concertReserv.domain.reservation.message;

import com.hhplus.concertReserv.domain.reservation.message.entity.PaymentOutbox;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentOutboxRepository {
    void save(PaymentOutbox outbox);

    void complete(PaymentOutbox outbox);

    Optional<PaymentOutbox> findByPaymentId(UUID paymentId);

    Optional<List<PaymentOutbox>> findPublishFailed();
}
