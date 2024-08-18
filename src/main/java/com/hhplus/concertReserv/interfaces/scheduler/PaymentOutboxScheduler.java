package com.hhplus.concertReserv.interfaces.scheduler;

import com.hhplus.concertReserv.domain.reservation.event.PaymentEvent;
import com.hhplus.concertReserv.domain.reservation.message.PaymentMessagePublisher;
import com.hhplus.concertReserv.domain.reservation.message.OutboxEnum;
import com.hhplus.concertReserv.domain.reservation.message.PaymentOutboxRepository;
import com.hhplus.concertReserv.domain.reservation.message.entity.PaymentOutbox;
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

    //TODO : 추상 클래스 outbox / Generic 적용을 통한 범용적인 OutboxService 디자인
    @Scheduled(cron = "0 0/1 * * * *")
    public void retryPublishKafkaMessage() {
        Optional<List<PaymentOutbox>> retryEventId = outboxRepository.findPublishFailed();
        if (retryEventId.isPresent()) {
            for (int i = 0; i < retryEventId.get().size(); i++) {
                PaymentEvent event = PaymentEvent.builder()
                        .paymentId(retryEventId.get().get(i).getPaymentId())
                        .status(OutboxEnum.COMPLETED.getStatus())
                        .build();

                // retry 횟수 +1
                PaymentOutbox outbox = retryEventId.get().get(i);
                outbox.setRetry(outbox.getRetry()+1);
                outboxRepository.save(outbox);

                messagePublisher.publish(event);
            }
        }
    }
}
