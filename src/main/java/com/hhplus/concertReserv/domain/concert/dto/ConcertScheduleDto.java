package com.hhplus.concertReserv.domain.concert.dto;

import com.hhplus.concertReserv.domain.concert.entity.ConcertSchedule;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ConcertScheduleDto {

    private UUID concertId;
    private String concertDt;

    public ConcertScheduleDto(ConcertSchedule concertSchedule){
        this.concertId = concertSchedule.getConcertId().getConcertId();
        this.concertDt = concertSchedule.getConcertDt().format(DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss.SSS"));

    }

    public ConcertScheduleDto(UUID concertId, LocalDateTime concertDt){
        this.concertId = concertId;
        this.concertDt = concertDt.format(DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss.SSS"));
    }

}
