package com.hhplus.concertReserv.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReserveDto {
    String result;
    String message;
    PaymentDto payment;

    List<ConcertDto> concert;

    public ReserveDto(){
        this.result = "success";
    }


}
