package com.hhplus.concertReserv.domain.reservation.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @InjectMocks
    private PaymentService paymentService;


    @DisplayName("결제금액이 음수인 경우 exception 처리")
    @Test
    void paid() {

        Long amount = -1L;
        UUID memberId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");

        //then
        assertThat(paymentService.paid(memberId, amount).getResult()).isEqualTo(false);
    }
}