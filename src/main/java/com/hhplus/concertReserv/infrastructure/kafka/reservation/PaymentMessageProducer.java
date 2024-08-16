package com.hhplus.concertReserv.infrastructure.kafka.reservation;

import com.hhplus.concertReserv.domain.reservation.event.PaymentEvent;
import com.hhplus.concertReserv.domain.reservation.kafka.PaymentMessagePublisher;
import com.hhplus.concertReserv.infrastructure.kafka.KafkaMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class PaymentMessageProducer implements PaymentMessagePublisher {

    @Value("${spring.kafka.topic.payment}")
    private String paymentTopic;
    private final KafkaTemplate<String, KafkaMessage<?>> kafkaTemplate;


    public PaymentMessageProducer(KafkaTemplate<String, KafkaMessage<?>> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(PaymentEvent paymentEvent) {
        KafkaMessage<PaymentEvent> message = new KafkaMessage<>();
        message.setPublishDt(LocalDateTime.now());
        message.setPayload(paymentEvent);

        log.info(">>> Kafka producer Send start : Payment Info {}", message);

        kafkaTemplate.send(paymentTopic, message);

        log.info(">>> Kafka producer Send end : Payment Info {}", message);
    }
}
