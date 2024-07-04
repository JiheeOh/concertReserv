package com.hhplus.concertReserv.application;

import com.hhplus.concertReserv.domain.dto.ReserveDto;
import com.hhplus.concertReserv.domain.service.ReserveService;
import org.springframework.stereotype.Component;

@Component
public class ReserveFacade {
    private final ReserveService reserveService;

    public ReserveFacade(ReserveService reserveService) {
        this.reserveService = reserveService;
    }

    public ReserveDto findReserveAvailable(String authorization){
        return reserveService.findReserveAvailable(authorization);
    }

    public ReserveDto applySeat(String authorization, ReserveCommand.ApplySeat requestBody){
        return reserveService.applySeat(requestBody.seatId(),requestBody.concertDate(),requestBody.seatId());
    }
}
