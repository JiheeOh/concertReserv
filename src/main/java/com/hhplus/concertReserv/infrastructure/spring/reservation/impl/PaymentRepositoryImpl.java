package com.hhplus.concertReserv.infrastructure.spring.reservation.impl;

import com.hhplus.concertReserv.domain.reservation.entity.Payment;
import com.hhplus.concertReserv.domain.reservation.repositories.PaymentRepository;
import com.hhplus.concertReserv.infrastructure.spring.reservation.jpaRepository.PaymentJPARepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {
    private final PaymentJPARepository paymentJPARepository;

    public PaymentRepositoryImpl(PaymentJPARepository paymentJPARepository){
        this.paymentJPARepository = paymentJPARepository;

    }
    @Override
    public Payment saveAndFlush(Payment payment) {
        return paymentJPARepository.saveAndFlush(payment);
    }

    @Override
    public List<Payment> findNotPaidToken() {
        return paymentJPARepository.findNotPaidToken();
    }

    @Override
    public Optional<Payment> findById(UUID paymentId) {
        return paymentJPARepository.findById(paymentId);
    }
}
