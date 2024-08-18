package com.hhplus.concertReserv.infrastructure.kafka.reservation;

import com.hhplus.concertReserv.domain.reservation.event.ReservationEvent;
import com.hhplus.concertReserv.domain.reservation.message.ReservationMessagePublisher;
import com.hhplus.concertReserv.infrastructure.kafka.KafkaMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class ReservationMessageProducer implements ReservationMessagePublisher {

    @Value("${spring.kafka.topic.reservation}")
    private String reservationTopic;
    private final KafkaTemplate<String, KafkaMessage<?>> kafkaTemplate;

    public ReservationMessageProducer(KafkaTemplate<String, KafkaMessage<?>> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(ReservationEvent reservationEvent) {
        KafkaMessage<ReservationEvent> message = new KafkaMessage<>();
        message.setPublishDt(LocalDateTime.now());
        message.setPayload(reservationEvent);

        log.info(">>> Kafka producer Send start : Reservation Info {}",message);
        // TODO key
        kafkaTemplate.send(reservationTopic, message);
        log.info(">>> Kafka producer Send end : Reservation Info {}",message);
    }
}
