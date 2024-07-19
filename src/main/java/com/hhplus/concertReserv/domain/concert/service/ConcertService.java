package com.hhplus.concertReserv.domain.concert.service;

import com.hhplus.concertReserv.domain.concert.dto.ConcertDto;
import com.hhplus.concertReserv.domain.concert.repositories.ConcertScheduleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ConcertService {
    private final ConcertScheduleRepository concertScheduleRepository;

    public ConcertService(ConcertScheduleRepository concertScheduleRepository) {
        this.concertScheduleRepository = concertScheduleRepository;
    }

    public List<ConcertDto> findConcertList() {
        List<ConcertDto> result = new ArrayList<>();
        log.info(" ==== findConcertList() start ====");
        try{
            result=  concertScheduleRepository.findConcertList();
        }catch (Exception e){
            log.error(e.toString());
        }
        log.info(" ==== findConcertList() end ====");
        return result;
    }
}
