package com.hhplus.concertReserv.infrastructure.concert.impl;

import com.hhplus.concertReserv.domain.concert.dto.ConcertDto;
import com.hhplus.concertReserv.domain.concert.entity.ConcertSchedule;
import com.hhplus.concertReserv.domain.concert.repositories.ConcertScheduleRepository;
import com.hhplus.concertReserv.infrastructure.concert.repository.ConcertScheduleJPARepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ConcertScheduleRepositoryImpl implements ConcertScheduleRepository {
    private final ConcertScheduleJPARepository concertScheduleJPARepository;
    public ConcertScheduleRepositoryImpl(ConcertScheduleJPARepository concertScheduleJPARepository){
        this.concertScheduleJPARepository = concertScheduleJPARepository;
    }
    @Override
    public List<ConcertDto> findConcertList() {
        List<ConcertSchedule> concertScheduleList = concertScheduleJPARepository.findAll();

        return concertScheduleList.stream().map(ConcertDto::new).toList();
    }
}
