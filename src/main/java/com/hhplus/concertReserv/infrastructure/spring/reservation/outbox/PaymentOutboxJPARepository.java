package com.hhplus.concertReserv.infrastructure.spring.reservation.outbox;

import com.hhplus.concertReserv.domain.reservation.message.entity.PaymentOutbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentOutboxJPARepository extends JpaRepository<PaymentOutbox, UUID> {
    @Query("select p from PaymentOutbox p where p.paymentId = :paymentId")
    Optional<PaymentOutbox> findByPaymentId(UUID paymentId);

    @Query(value = "select * from Payment_Outbox p where p.status = 'INIT' and p.event_create_Dt > NOW() - INTERVAL 2 MINUTE and p.retry < 10",nativeQuery = true)
    Optional<List<PaymentOutbox>> findPublishFailed();
}
