package com.hhplus.concertReserv.application;

import com.hhplus.concertReserv.domain.reservation.dto.ReserveDto;
import com.hhplus.concertReserv.domain.reservation.service.ReservationService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ReserveFacade {

    private final ReservationService reservationService;

    public ReserveFacade(ReservationService reservationService) {
        this.reservationService = reservationService;

    }

    public ReserveDto findReserveAvailable(UUID concertId) {
        return reservationService.findReserveAvailable(concertId);
    }

    public ReserveDto applySeat(ReserveCommand.ApplySeat requestBody) {
        return reservationService.applySeat(requestBody.memberId(), requestBody.seatId());
    }
}
