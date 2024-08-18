package com.hhplus.concertReserv.domain.reservation.message;

import com.hhplus.concertReserv.domain.common.exception.ErrorCode;
import com.hhplus.concertReserv.domain.reservation.event.ReservationEvent;
import com.hhplus.concertReserv.domain.reservation.message.entity.ReservationOutbox;
import com.hhplus.concertReserv.exception.UserNotFoundException;
import com.hhplus.concertReserv.infrastructure.kafka.KafkaMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class ReservationOutboxWriter {

    private final ReservationOutboxRepository outboxRepository;

    public ReservationOutboxWriter(ReservationOutboxRepository outboxRepository) {
        this.outboxRepository = outboxRepository;
    }

    public void save(ReservationEvent reservationEvent) {
        log.info(String.format(" ==== outbox save started !!! seatId : %s , userId : %s  ====", reservationEvent.getSeatId(), reservationEvent.getUserId()));

        ReservationOutbox outbox = new ReservationOutbox();
        outbox.setStatus(OutboxEnum.INIT.getStatus());
        outbox.setEventCreateDt(LocalDateTime.now());
        outbox.setSeatId(reservationEvent.getSeatId());
        outbox.setUserId(reservationEvent.getUserId());

        outboxRepository.save(outbox);

        log.info(String.format(" ==== outbox save end !!! seatId : %s , userId : %s  ====", reservationEvent.getSeatId(), reservationEvent.getUserId()));

    }

    public void complete(KafkaMessage<ReservationEvent> message) {
        log.info(String.format(" ==== outbox complete started !!! seatId : %s , userId : %s  ====", message.getPayload().getSeatId(), message.getPayload().getUserId()));
        ReservationOutbox outbox = outboxRepository.findBySeatIdUserId(message.getPayload().getSeatId(), message.getPayload().getUserId()).orElseThrow(()-> new UserNotFoundException(ErrorCode.RESERVATION_OUTBOX_NOT_FOUND));
        outbox.setStatus(OutboxEnum.COMPLETED.getStatus());
        outbox.setEventCompletedDt(LocalDateTime.now());

        outboxRepository.complete(outbox);

        log.info(String.format(" ==== outbox complete end !!! seatId : %s , userId : %s  ====", message.getPayload().getSeatId(), message.getPayload().getUserId()));

    }
}
