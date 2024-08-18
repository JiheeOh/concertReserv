package com.hhplus.concertReserv.interfaces.consumer.reservation;

import com.hhplus.concertReserv.domain.reservation.event.PaymentEvent;
import com.hhplus.concertReserv.domain.reservation.message.PaymentOutboxWriter;
import com.hhplus.concertReserv.infrastructure.kafka.KafkaMessage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.adapter.ConsumerRecordMetadata;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Getter
public class PaymentConsumer {

    private final PaymentOutboxWriter outboxWriter;

    public PaymentConsumer(PaymentOutboxWriter outboxWriter) {
        this.outboxWriter = outboxWriter;

    }

    @KafkaListener(topics = "payment",containerFactory = "containerPaymentListenerFactory", groupId ="payment_group")
    private void paymentListener(@Payload KafkaMessage<PaymentEvent> message, Acknowledgment ack, ConsumerRecordMetadata metaData){
        log.info("> Kafka Consumer Read Start [Payment Event] :{}",message);
        ack.acknowledge();
        outboxWriter.complete(message);
        log.info("> Kafka Consumer Read End [Payment Event] : {}",message);
    }


}
