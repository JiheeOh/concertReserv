package com.hhplus.concertReserv.infrastructure.kafka.reservation;

import com.hhplus.concertReserv.domain.reservation.message.PaymentOutboxRepository;
import com.hhplus.concertReserv.domain.reservation.message.entity.PaymentOutbox;
import com.hhplus.concertReserv.infrastructure.spring.reservation.outbox.PaymentOutboxJPARepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class PaymentOutboxRepositoryImpl implements PaymentOutboxRepository {

private final PaymentOutboxJPARepository paymentOutboxJPARepository;

    public PaymentOutboxRepositoryImpl(PaymentOutboxJPARepository paymentOutboxJPARepository) {
        this.paymentOutboxJPARepository = paymentOutboxJPARepository;
    }

    @Override
    public void save(PaymentOutbox outbox) {
        paymentOutboxJPARepository.save(outbox);
    }

    @Override
    public void complete(PaymentOutbox outbox) {
        paymentOutboxJPARepository.save(outbox);
    }

    @Override
    public Optional<PaymentOutbox> findByPaymentId(UUID paymentId) {
        return paymentOutboxJPARepository.findByPaymentId(paymentId);
    }

    /**
     * 이벤트 생성시간이 10분이상이면서, retry 횟수가 10보다 적은 이벤트 가져오기
     */
    @Override
    public Optional<List<PaymentOutbox>> findPublishFailed() {
        return paymentOutboxJPARepository.findPublishFailed();
    }
}
