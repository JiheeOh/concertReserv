package com.hhplus.concertReserv.infrastructure.reservation.jpaRepository;

import com.hhplus.concertReserv.domain.reservation.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PaymentJPARepository extends JpaRepository<Payment, UUID> {

    @Query("SELECT p FROM Payment p WHERE p.dueTime > current timestamp ")
    List<Payment> findNotPaidToken();
}
