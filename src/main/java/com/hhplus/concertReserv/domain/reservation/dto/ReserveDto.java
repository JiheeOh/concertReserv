package com.hhplus.concertReserv.domain.reservation.dto;

import com.hhplus.concertReserv.domain.concert.dto.ConcertDto;
import lombok.Getter;
import lombok.Setter;

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

    public ReserveDto(boolean result, String message) {
        this.result = result;
        this.message = message;
    }


    public boolean getResult() {
        return this.result;
    }
}
