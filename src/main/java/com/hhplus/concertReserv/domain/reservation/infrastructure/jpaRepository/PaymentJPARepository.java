package com.hhplus.concertReserv.domain.reservation.infrastructure.jpaRepository;

import com.hhplus.concertReserv.domain.reservation.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentJPARepository extends JpaRepository<Payment, UUID> {

}
