package com.hhplus.concertReserv.interfaces.consumer.reservation;

import com.hhplus.concertReserv.domain.reservation.event.PaymentEvent;
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

@Component
@Slf4j
@Getter
public class PaymentConsumer {

    private BlockingQueue<KafkaMessage<PaymentEvent>> records = new LinkedBlockingQueue<>();
    @KafkaListener(topics = "payment",containerFactory = "containerPaymentListenerFactory", groupId ="payment_group")
    private void reservationListener(@Payload KafkaMessage<PaymentEvent> paymentEvent, Acknowledgment ack, ConsumerRecordMetadata metaData){
        log.info("> Kafka Consumer Read Start [Payment Event] :{}",paymentEvent);
        records.add(paymentEvent);
        ack.acknowledge();
        log.info("> Kafka Consumer Read End [Payment Event] : {}",paymentEvent);
    }


}
