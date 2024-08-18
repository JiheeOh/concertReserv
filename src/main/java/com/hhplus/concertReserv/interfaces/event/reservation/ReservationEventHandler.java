package com.hhplus.concertReserv.interfaces.event.reservation;

import com.hhplus.concertReserv.domain.reservation.event.ReservationEvent;
import com.hhplus.concertReserv.domain.reservation.kafka.ReservationMessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Slf4j
public class ReservationEventHandler {

//    private final ReservationSender reservationSender;

    private final ReservationMessagePublisher reservationMessagePublisher;

    public ReservationEventHandler(ReservationMessagePublisher reservationMessagePublisher) {
        this.reservationMessagePublisher = reservationMessagePublisher;

    }

//    @Async
//    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
//    public void publish(ReservationEvent reservationEvent){
//        reservationSender.sendInfo(reservationEvent);
//    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void publishMessage(ReservationEvent reservationEvent){
        reservationMessagePublisher.publish(reservationEvent);

    }

}
