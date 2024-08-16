package com.hhplus.concertReserv.domain.reservation.kafka.outbox;

import com.hhplus.concertReserv.domain.reservation.event.PaymentEvent;
import com.hhplus.concertReserv.domain.reservation.kafka.outbox.entity.PaymentOutbox;
import com.hhplus.concertReserv.domain.reservation.kafka.outbox.entity.ReservationOutbox;
import com.hhplus.concertReserv.infrastructure.kafka.KafkaMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class PaymentOutboxWriter {

    private final PaymentOutboxRepository outboxRepository;

    public PaymentOutboxWriter(PaymentOutboxRepository outboxRepository) {
        this.outboxRepository = outboxRepository;
    }

    public void save(PaymentEvent paymentEvent) {
        log.info(String.format(" ==== outbox save started !!! paymentId : %s ====",paymentEvent.getPaymentId()));

        PaymentOutbox outbox = new PaymentOutbox();
        outbox.setStatus(OutboxEnum.INIT.getStatus());
        outbox.setEventCreateDt(LocalDateTime.now());
        outbox.setPaymentId(paymentEvent.getPaymentId());

        outboxRepository.save(outbox);
        log.info(String.format(" ==== outbox save end !!! paymentId : %s ====",paymentEvent.getPaymentId()));
    }

    public void complete(KafkaMessage<PaymentEvent> message) {
        log.info(String.format(" ==== outbox save started !!! paymentId : %s ====",message.getPayload().getPaymentId()));
        PaymentOutbox outbox = outboxRepository.findByPaymentId(message.getPayload().getPaymentId());
        outbox.setStatus(OutboxEnum.COMPLETED.getStatus());
        outbox.setEventCompletedDt(LocalDateTime.now());

        outboxRepository.complete(outbox);
        log.info(String.format(" ==== outbox save end !!! paymentId : %s ====",message.getPayload().getPaymentId()));
    }
}