package com.hhplus.concertReserv.infrastructure.Impl;

import com.hhplus.concertReserv.domain.entity.Payment;
import com.hhplus.concertReserv.domain.repository.PaymentRepository;
import com.hhplus.concertReserv.infrastructure.jpaRepository.PaymentJPARepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {
    private PaymentJPARepository paymentJPARepository;

    public PaymentRepositoryImpl(PaymentJPARepository paymentJPARepository){
        this.paymentJPARepository = paymentJPARepository;

    }
    @Override
    public Payment save(Payment payment) {
        return paymentJPARepository.save(payment);
    }

    @Override
    public List<Payment> findNotPaidToken() {
        return new ArrayList<>();
    }

    @Override
    public Optional<Payment> findById(UUID paymentId) {
        return paymentJPARepository.findById(paymentId);
    }
}
