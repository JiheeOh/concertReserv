package com.hhplus.concertReserv.domain.concert.repositories;

import com.hhplus.concertReserv.domain.concert.dto.ConcertDto;
import com.hhplus.concertReserv.domain.concert.dto.ConcertScheduleDto;
import com.hhplus.concertReserv.domain.concert.entity.ConcertSchedule;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ConcertScheduleRepository {
    List<ConcertDto> findConcertList();

    List<ConcertScheduleDto> findAvailableSchedule(UUID concertId);

    void updateDelYnToN(UUID concertId, LocalDateTime concertDt);
}
