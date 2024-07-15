package com.hhplus.concertReserv.domain.concert.service;

import com.hhplus.concertReserv.domain.concert.dto.ConcertDto;
import com.hhplus.concertReserv.domain.concert.repositories.ConcertScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConcertService {
    private  final ConcertScheduleRepository concertScheduleRepository;

    public ConcertService(ConcertScheduleRepository concertScheduleRepository){
        this.concertScheduleRepository = concertScheduleRepository;
    }
    public List<ConcertDto> findConcertList() {
        return concertScheduleRepository.findConcertList();
    }
}
