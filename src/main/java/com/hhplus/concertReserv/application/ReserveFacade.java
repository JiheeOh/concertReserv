package com.hhplus.concertReserv.application;

import com.hhplus.concertReserv.domain.dto.ReserveDto;
import com.hhplus.concertReserv.domain.service.ReservationService;
import com.hhplus.concertReserv.domain.service.ValidationService;
import com.hhplus.concertReserv.exception.TokenNotFoundException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ReserveFacade {
    private final ValidationService validationService;
    private final ReservationService reservationService;

    public ReserveFacade(ReservationService reservationService, ValidationService validationService) {
        this.reservationService = reservationService;
        this.validationService = validationService;
    }

    public ReserveDto findReserveAvailable(UUID concertId, Long tokenId){
        if(validationService.isActivateToken(tokenId)){
            throw new TokenNotFoundException("Token is deActivated",500);
        }
        return reservationService.findReserveAvailable(concertId);
    }

    public ReserveDto applySeat(ReserveCommand.ApplySeat requestBody){
        if(validationService.isActivateToken(requestBody.tokenId())){
            throw new TokenNotFoundException("Token is deActivated",500);
        }
        return reservationService.applySeat(requestBody.memberId(), requestBody.seatId());
    }
}
