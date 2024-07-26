package com.hhplus.concertReserv.domain.concert.dto;

import com.hhplus.concertReserv.domain.concert.entity.Seat;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class SeatDto {

    String seatClass;
    UUID seatId;
    Long seatNo;
    Long price;
    String status;

    public SeatDto(Seat seat){
        this.seatClass = seat.getSeatClass();
        this.seatId = seat.getSeatId();
        this.seatNo = seat.getSeatNo();
        this.price= seat.getPrice();
        this.status = seat.getStatus();
    }
}
