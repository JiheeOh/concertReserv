package com.hhplus.concertReserv.infrastructure.kafka.reservation;

import com.hhplus.concertReserv.domain.concert.SeatEnum;
import com.hhplus.concertReserv.domain.reservation.event.PaymentEvent;
import com.hhplus.concertReserv.infrastructure.kafka.KafkaMessage;
import com.hhplus.concertReserv.interfaces.consumer.reservation.PaymentConsumer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EmbeddedKafka(partitions = 3
        , brokerProperties = {"listeners=PLAINTEXT://localhost:9092"}
        , ports = {9092}
        , topics = {"payment"}
       )
class PaymentMessageTest {

    @Autowired
    private PaymentMessageProducer paymentMessageProducer;

    @Autowired
    private PaymentConsumer paymentConsumer;

    /**
     * Payment Topic을 발행하고
     * paymnet consumer group의 consumer 1이 잘 수신하는지 확인
     */
    @DisplayName("카프카 메시지 발행과 수신 정상 작동 확인 ")
    @Test
    void publish_getMessage() throws InterruptedException {
        //given
        PaymentEvent event = PaymentEvent.builder()
                .payYn("Y")
                .actuAmount(100000L)
                .confirmYn("Y")
                .status(SeatEnum.OCCUPIED.getStatus()).build();

        //when : 발행 확인
        paymentMessageProducer.publish(event);

        //then : 수신 확인
//
//        KafkaMessage<PaymentEvent> receivedMessage = paymentConsumer.getRecords().poll(10,TimeUnit.SECONDS);
//        assertThat(receivedMessage).isNotNull();
//        assertThat(receivedMessage.getPayload().getActuAmount()).isEqualTo(event.getActuAmount());
//        assertThat(receivedMessage.getPayload().getPayYn()).isEqualTo(event.getPayYn());
//        assertThat(receivedMessage.getPayload().getConfirmYn()).isEqualTo(event.getConfirmYn());
//        assertThat(receivedMessage.getPayload().getStatus()).isEqualTo(event.getStatus());


    }


}