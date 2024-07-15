package com.hhplus.concertReserv.application;

import com.hhplus.concertReserv.domain.reservation.dto.ReserveDto;
import com.hhplus.concertReserv.domain.reservation.service.ReservationService;
import com.hhplus.concertReserv.domain.token.service.TokenService;
import com.hhplus.concertReserv.exception.TokenNotFoundException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ReserveFacade {
    private final TokenService tokenService;
    private final ReservationService reservationService;

    public ReserveFacade(ReservationService reservationService, TokenService tokenService) {
        this.reservationService = reservationService;
        this.tokenService = tokenService;
    }

    public ReserveDto findReserveAvailable(UUID concertId, Long tokenId){
        if(tokenService.isActivateToken(tokenId)){
            throw new TokenNotFoundException("Token is deActivated",500);
        }
        return reservationService.findReserveAvailable(concertId);
    }

    public ReserveDto applySeat(ReserveCommand.ApplySeat requestBody){
        if(tokenService.isActivateToken(requestBody.tokenId())){
            throw new TokenNotFoundException("Token is deActivated",500);
        }
        return reservationService.applySeat(requestBody.memberId(), requestBody.seatId());
    }
}
