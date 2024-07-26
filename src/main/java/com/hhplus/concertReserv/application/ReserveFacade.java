package com.hhplus.concertReserv.application;

import com.hhplus.concertReserv.domain.reservation.dto.ReserveDto;
import com.hhplus.concertReserv.domain.reservation.dto.ReserveInfoDto;
import com.hhplus.concertReserv.domain.reservation.service.PaymentService;
import com.hhplus.concertReserv.domain.reservation.service.ReservationService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ReserveFacade {

    private final ReservationService reservationService;
    private final PaymentService paymentService;

    public ReserveFacade(ReservationService reservationService, PaymentService paymentService) {
        this.reservationService = reservationService;
        this.paymentService = paymentService;
    }

    public ReserveDto findReserveAvailable(UUID concertId) {
        return reservationService.findReserveAvailable(concertId);
    }

    public ReserveDto applySeat(ReserveCommand.ApplySeat requestBody) {
        ReserveInfoDto appliedSeat = reservationService.applySeat(requestBody.memberId(), requestBody.seatId());
        if (!appliedSeat.isResult()){
            return new ReserveDto(false,appliedSeat.getMessage());
        }
        return paymentService.createPayment(appliedSeat);
    }
}
