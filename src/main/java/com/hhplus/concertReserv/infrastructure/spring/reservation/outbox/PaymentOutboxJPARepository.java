package com.hhplus.concertReserv.infrastructure.spring.reservation.outbox;

import com.hhplus.concertReserv.domain.reservation.kafka.outbox.entity.PaymentOutbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface PaymentOutboxJPARepository extends JpaRepository<PaymentOutbox, UUID> {
    @Query("select p from PaymentOutbox p where p.paymentId = :paymentId")
    PaymentOutbox findByPaymentId(UUID paymentId);
}
