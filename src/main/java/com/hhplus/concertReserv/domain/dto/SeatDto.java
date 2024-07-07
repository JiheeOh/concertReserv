package com.hhplus.concertReserv.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class SeatDto {

    String seatClass;
    UUID seatId;
    String seatNm;
    Long seatPrice;
}
