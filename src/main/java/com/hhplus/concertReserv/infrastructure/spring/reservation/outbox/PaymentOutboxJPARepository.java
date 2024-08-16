package com.hhplus.concertReserv.infrastructure.spring.reservation.outbox;

import com.hhplus.concertReserv.domain.reservation.kafka.outbox.entity.PaymentOutbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentOutboxJPARepository extends JpaRepository<PaymentOutbox, UUID> {
    @Query("select p from PaymentOutbox p where p.paymentId = :paymentId")
    Optional<PaymentOutbox> findByPaymentId(UUID paymentId);

    @Query(value = "select p from PaymentOutbox r where r.status = 'INIT' and r.eventCreateDt > NOW() - INTERVAL 10 MINUTE ",nativeQuery = true)
    Optional<List<PaymentOutbox>> findPublishFailed();
}
