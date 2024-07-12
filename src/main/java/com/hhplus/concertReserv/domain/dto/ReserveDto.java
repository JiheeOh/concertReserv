package com.hhplus.concertReserv.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ReserveDto {
    boolean result;
    String message;
    PaymentDto payment;
    ConcertDto concert;

    public ReserveDto(){
        this.result = true;
        this.message= "";
    }


    public boolean getResult() {
        return this.result;
    }
}
