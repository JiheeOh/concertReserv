package com.hhplus.concertReserv.interfaces.consumer.reservation;

import com.hhplus.concertReserv.domain.reservation.event.ReservationEvent;
import com.hhplus.concertReserv.domain.reservation.kafka.outbox.ReservationOutboxWriter;
import com.hhplus.concertReserv.infrastructure.kafka.KafkaMessage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.adapter.ConsumerRecordMetadata;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Getter
@Component
@Slf4j
public class ReservationConsumer {


    private final ReservationOutboxWriter outboxWriter;

    public ReservationConsumer(ReservationOutboxWriter outboxWriter) {
        this.outboxWriter = outboxWriter;
    }


    @KafkaListener(topics = "reservation",containerFactory = "containerReservationListenerFactory", groupId ="reservation_group")
    private void reservationListener(@Payload KafkaMessage<ReservationEvent> message, Acknowledgment ack, ConsumerRecordMetadata metaData){
        log.info("> Kafka Consumer Read Start [Reservation Event] :{}",message);
        ack.acknowledge();
        outboxWriter.complete(message);
        log.info("> Kafka Consumer Read End [Reservation Event] : {}",message);
    }

}
