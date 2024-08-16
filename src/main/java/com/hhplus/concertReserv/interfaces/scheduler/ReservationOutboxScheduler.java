package com.hhplus.concertReserv.interfaces.scheduler;

import com.hhplus.concertReserv.domain.reservation.event.ReservationEvent;
import com.hhplus.concertReserv.domain.reservation.kafka.ReservationMessagePublisher;
import com.hhplus.concertReserv.domain.reservation.kafka.outbox.ReservationOutboxRepository;
import com.hhplus.concertReserv.domain.reservation.kafka.outbox.entity.ReservationOutbox;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class ReservationOutboxScheduler {

    private final ReservationOutboxRepository outboxRepository;

    private final ReservationMessagePublisher reservationMessagePublisher;

    public ReservationOutboxScheduler(ReservationOutboxRepository outboxRepository, ReservationMessagePublisher reservationMessagePublisher) {
        this.outboxRepository = outboxRepository;
        this.reservationMessagePublisher = reservationMessagePublisher;
    }

    /**
     * 1분 마다 작동하면서
     * 10분 이전에 publish publish가 되었지만 INIT상태인 데이터 다시 publish 실행
     */
    @Scheduled(cron = "0 0/1 * * * *")
    public void retryPublishKafkaMessage() {
        Optional<List<ReservationOutbox>> retryEventId = outboxRepository.findPublishFailed();
        if (retryEventId.isPresent()) {
            for (int i = 0; i < retryEventId.get().size(); i++) {
                ReservationEvent event = ReservationEvent.builder()
                        .seatId(retryEventId.get().get(i).getSeatId())
                        .userId(retryEventId.get().get(i).getUserId())
                        .build();

                // retry 횟수 +1
                ReservationOutbox outbox = retryEventId.get().get(i);
                outbox.setRetry(outbox.getRetry()+1);
                outboxRepository.save(outbox);

                reservationMessagePublisher.publish(event);
            }
        }
    }
}
