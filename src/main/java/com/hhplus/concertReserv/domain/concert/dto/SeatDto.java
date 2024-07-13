package com.hhplus.concertReserv.domain.concert.dto;

import com.hhplus.concertReserv.domain.concert.entity.Seat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeatDto {

    String seatClass;
    Long seatNo;
    Long price;

    public SeatDto(Seat seat){
        this.seatClass = seat.getSeatClass();
        this.seatNo = seat.getSeatNo();
        this.price= seat.getPrice();
    }
}
