package com.hhplus.concertReserv.domain.reservation.dto;

import com.hhplus.concertReserv.domain.reservation.entity.Reservation;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ReserveInfoDto {
    private boolean result;
    private String message;
    private Reservation reservation;
    private UUID tokenId;

    public ReserveInfoDto(){
        this.result =true;
        this.message = "";
    }
}
