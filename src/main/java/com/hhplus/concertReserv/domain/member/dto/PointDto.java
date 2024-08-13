package com.hhplus.concertReserv.domain.member.dto;

import com.hhplus.concertReserv.domain.reservation.event.PaymentEvent;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class PointDto {
    boolean result;
    String message;
    Long point;
    UUID memberId;
    UUID concertId;
    PaymentEvent paymentEvent;

    public PointDto(){
        this.result=true;
    }

    public boolean getResult() {
        return this.result;
    }
}
