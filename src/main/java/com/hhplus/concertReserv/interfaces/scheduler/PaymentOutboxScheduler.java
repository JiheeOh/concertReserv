package com.hhplus.concertReserv.interfaces.scheduler;

import com.hhplus.concertReserv.domain.reservation.event.PaymentEvent;
import com.hhplus.concertReserv.domain.reservation.kafka.PaymentMessagePublisher;
import com.hhplus.concertReserv.domain.reservation.kafka.outbox.PaymentOutboxRepository;
import com.hhplus.concertReserv.domain.reservation.kafka.outbox.entity.PaymentOutbox;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class PaymentOutboxScheduler {

    private final PaymentOutboxRepository outboxRepository;

    private final PaymentMessagePublisher messagePublisher;

    public PaymentOutboxScheduler(PaymentOutboxRepository outboxRepository, PaymentMessagePublisher messagePublisher) {
        this.outboxRepository = outboxRepository;
        this.messagePublisher = messagePublisher;
    }

    /**
     * 1분 마다 작동하면서
     * 10분 이전에 publish publish가 되었지만 INIT상태인 데이터 다시 publish 실행
     */
    @Scheduled(cron = "0 0/1 * * * *")
    public void retryPublishKafkaMessage() {
        Optional<List<PaymentOutbox>> retryEventId = outboxRepository.findPublishFailed();
        if (retryEventId.isPresent()) {
            for (int i = 0; i < retryEventId.get().size(); i++) {
                PaymentEvent event = PaymentEvent.builder().build();
                messagePublisher.publish(event);
            }
        }
    }
}