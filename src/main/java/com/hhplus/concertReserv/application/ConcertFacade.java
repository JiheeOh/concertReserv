package com.hhplus.concertReserv.application;

import com.hhplus.concertReserv.domain.concert.dto.ConcertDto;
import com.hhplus.concertReserv.domain.concert.service.ConcertService;
import com.hhplus.concertReserv.domain.token.service.TokenService;
import com.hhplus.concertReserv.exception.TokenNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConcertFacade {

    private final ConcertService concertService;

    public ConcertFacade(ConcertService concertService) {
        this.concertService = concertService;
    }

    public List<ConcertDto> findConcertList() {
        return concertService.findConcertList();
    }
}
