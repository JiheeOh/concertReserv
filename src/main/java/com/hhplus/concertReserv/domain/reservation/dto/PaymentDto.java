package com.hhplus.concertReserv.domain.reservation.dto;

import com.hhplus.concertReserv.domain.reservation.entity.Payment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class PaymentDto {
    UUID payId;
    LocalDateTime dueTime;
    Long payAmount;

    public PaymentDto(Payment payment){
        this.dueTime = payment.getDueTime();
        this.payId = payment.getPaymentId();
        this.payAmount = payment.getPrice();

    }
}
