package com.hhplus.concertReserv.domain.reservation.repositories;

import com.hhplus.concertReserv.domain.reservation.entity.Payment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository {
    Payment save(Payment payment);

    List<Payment> findNotPaidToken();

    Optional<Payment> findById(UUID paymentId);
}
