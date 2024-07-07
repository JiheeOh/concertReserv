package com.hhplus.concertReserv.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ConcertDto {

    UUID concertId;
    String concertNm;
    LocalDateTime concertDate;
    String concertHall;
    List<SeatDto> seat;

}
