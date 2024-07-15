package com.hhplus.concertReserv.application;

import com.hhplus.concertReserv.domain.concert.dto.ConcertDto;
import com.hhplus.concertReserv.domain.concert.service.ConcertService;
import com.hhplus.concertReserv.domain.token.service.TokenService;
import com.hhplus.concertReserv.exception.TokenNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConcertFacade {
    private final TokenService tokenService;

    private final ConcertService concertService;

    public ConcertFacade( TokenService tokenService, ConcertService concertService){
        this.tokenService = tokenService;
        this.concertService = concertService;
    }
    public List<ConcertDto> findConcertList(Long tokenId) {
        if(tokenService.isActivateToken(tokenId)){
            throw new TokenNotFoundException("Token is deActivated",500);
        }
        return concertService.findConcertList();
    }
}
