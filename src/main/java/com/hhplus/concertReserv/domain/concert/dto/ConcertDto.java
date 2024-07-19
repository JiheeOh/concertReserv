package com.hhplus.concertReserv.domain.concert.dto;

import com.hhplus.concertReserv.domain.concert.entity.ConcertSchedule;
import com.hhplus.concertReserv.domain.concert.entity.Seat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
public class ConcertDto {

    UUID concertId;
    String concertNm;
    LocalDateTime concertDate;
    String hallNm;
    List<SeatDto> seat;

    public ConcertDto(Seat seat) {
        this.concertId = seat.getConcertSchedule().getConcertId().getConcertId();
        this.concertNm = seat.getConcertSchedule().getConcertId().getConcertNm();
        this.concertDate = seat.getConcertSchedule().getConcertDt();
        this.hallNm = seat.getConcertSchedule().getHallNm();
    }

    public ConcertDto(ConcertSchedule concertSchedule) {
        this.concertId = concertSchedule.getConcertId().getConcertId();
        this.concertNm = concertSchedule.getConcertId().getConcertNm();
        this.concertDate = concertSchedule.getConcertDt();
        this.hallNm = concertSchedule.getHallNm();
    }

}
