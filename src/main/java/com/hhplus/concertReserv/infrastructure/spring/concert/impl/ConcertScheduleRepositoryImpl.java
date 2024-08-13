package com.hhplus.concertReserv.infrastructure.spring.concert.impl;

import com.hhplus.concertReserv.domain.concert.dto.ConcertDto;
import com.hhplus.concertReserv.domain.concert.dto.ConcertScheduleDto;
import com.hhplus.concertReserv.domain.concert.entity.ConcertSchedule;
import com.hhplus.concertReserv.domain.concert.repositories.ConcertScheduleRepository;
import com.hhplus.concertReserv.infrastructure.spring.concert.repository.ConcertScheduleJPARepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public class ConcertScheduleRepositoryImpl implements ConcertScheduleRepository {
    private final ConcertScheduleJPARepository concertScheduleJPARepository;
    public ConcertScheduleRepositoryImpl(ConcertScheduleJPARepository concertScheduleJPARepository){
        this.concertScheduleJPARepository = concertScheduleJPARepository;
    }
    @Override
    public List<ConcertDto> findConcertList() {
        List<ConcertSchedule> concertScheduleList = concertScheduleJPARepository.findAllByDelYn();

        return concertScheduleList.stream().map(ConcertDto::new).toList();
    }

    @Override
    public List<ConcertScheduleDto> findAvailableSchedule(UUID concertId) {
        List<ConcertSchedule> concertScheduleList = concertScheduleJPARepository.findByConcertId(concertId);
        return concertScheduleList.stream().map(ConcertScheduleDto::new).toList();
    }

    @Override
    public void updateDelYnToN(UUID concertId, LocalDateTime concertDt) {
        concertScheduleJPARepository.updateDelYnToN(concertId,concertDt);
    }
}
