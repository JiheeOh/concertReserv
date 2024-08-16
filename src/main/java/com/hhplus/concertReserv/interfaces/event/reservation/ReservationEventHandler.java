package com.hhplus.concertReserv.interfaces.event.reservation;

import com.hhplus.concertReserv.domain.reservation.event.ReservationEvent;
import com.hhplus.concertReserv.domain.reservation.kafka.ReservationMessagePublisher;
import com.hhplus.concertReserv.domain.reservation.kafka.outbox.ReservationOutboxWriter;
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

    private final ReservationOutboxWriter outboxWriter;
    public ReservationEventHandler(ReservationMessagePublisher reservationMessagePublisher, ReservationOutboxWriter outboxWriter) {
        this.reservationMessagePublisher = reservationMessagePublisher;
        this.outboxWriter = outboxWriter;
    }
//    @Async
//    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
//    public void publish(ReservationEvent reservationEvent){
//        reservationSender.sendInfo(reservationEvent);
//    }

    /**
     * 트랜잭션 commit 이후에 kafka발행
     */
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void publishMessage(ReservationEvent reservationEvent){
        reservationMessagePublisher.publish(reservationEvent);

    }

    /**
     * 트랜잭션 commit 전에 outbox에 데이터 저장
     */
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void saveOutbox(ReservationEvent reservationEvent){
        outboxWriter.save(reservationEvent);
    }

}
