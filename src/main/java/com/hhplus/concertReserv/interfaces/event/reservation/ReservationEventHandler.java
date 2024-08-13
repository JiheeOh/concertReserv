package com.hhplus.concertReserv.interfaces.event.reservation;

import com.hhplus.concertReserv.domain.reservation.event.ReservationEvent;
import com.hhplus.concertReserv.domain.sender.ReservationSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Slf4j
public class ReservationEventHandler {

    private final ReservationSender reservationSender;

    public ReservationEventHandler(ReservationSender reservationSender) {
        this.reservationSender = reservationSender;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void publish(ReservationEvent reservationEvent){
        reservationSender.sendInfo(reservationEvent);
    }
}
