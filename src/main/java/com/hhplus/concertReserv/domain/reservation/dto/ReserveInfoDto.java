package com.hhplus.concertReserv.domain.reservation.dto;

import com.hhplus.concertReserv.domain.reservation.entity.Reservation;
import com.hhplus.concertReserv.domain.token.entity.Token;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ReserveInfoDto {
    private boolean result;
    private String message;
    private Reservation reservation;
    private Token token;

    public ReserveInfoDto(){
        this.result =true;
        this.message = "";
    }

    public void setToken (UUID memberId, UUID concertId){
        this.token = new Token(memberId,concertId);
    }
}
