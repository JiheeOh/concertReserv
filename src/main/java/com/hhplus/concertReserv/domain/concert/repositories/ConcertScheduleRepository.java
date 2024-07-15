package com.hhplus.concertReserv.domain.concert.repositories;

import com.hhplus.concertReserv.domain.concert.dto.ConcertDto;

import java.util.List;

public interface ConcertScheduleRepository {
    List<ConcertDto> findConcertList();
}
