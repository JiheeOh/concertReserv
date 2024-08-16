package com.hhplus.concertReserv.infrastructure.kafka.reservation;

import com.hhplus.concertReserv.domain.reservation.kafka.outbox.PaymentOutboxRepository;
import com.hhplus.concertReserv.domain.reservation.kafka.outbox.entity.PaymentOutbox;
import com.hhplus.concertReserv.infrastructure.spring.reservation.outbox.PaymentOutboxJPARepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentOutboxRepositoryImpl implements PaymentOutboxRepository {

private final PaymentOutboxJPARepository paymentJPARepository;

    public PaymentOutboxRepositoryImpl(PaymentOutboxJPARepository paymentJPARepository) {
        this.paymentJPARepository = paymentJPARepository;
    }

    @Override
    public void save(PaymentOutbox outbox) {
        paymentJPARepository.save(outbox);
    }

    @Override
    public void complete(PaymentOutbox outbox) {
        paymentJPARepository.save(outbox);
    }

    @Override
    public PaymentOutbox findByPaymentId(UUID paymentId) {
        return paymentJPARepository.findByPaymentId(paymentId);
    }
}
