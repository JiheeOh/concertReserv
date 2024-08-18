package com.hhplus.concertReserv.interfaces.consumer.reservation;

import com.hhplus.concertReserv.domain.reservation.event.ReservationEvent;
import com.hhplus.concertReserv.infrastructure.kafka.KafkaMessage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.adapter.ConsumerRecordMetadata;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Getter
@Component
@Slf4j
public class ReservationConsumer {

    private BlockingQueue<KafkaMessage<ReservationEvent>> records = new LinkedBlockingQueue<>();


    @KafkaListener(topics = "reservation",containerFactory = "containerReservationListenerFactory", groupId ="reservation_group")
    private void reservationListener(@Payload KafkaMessage<ReservationEvent> message, Acknowledgment ack, ConsumerRecordMetadata metaData){
        log.info("> Kafka Consumer Read Start [Reservation Event] :{}",message);
        records.add(message);
        ack.acknowledge();
        log.info("> Kafka Consumer Read End [Reservation Event] : {}",message);
    }

}
