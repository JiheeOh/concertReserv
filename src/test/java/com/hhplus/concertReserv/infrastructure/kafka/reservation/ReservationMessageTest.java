package com.hhplus.concertReserv.infrastructure.kafka.reservation;

import com.hhplus.concertReserv.domain.reservation.event.ReservationEvent;
import com.hhplus.concertReserv.infrastructure.kafka.KafkaMessage;
import com.hhplus.concertReserv.interfaces.consumer.reservation.ReservationConsumer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EmbeddedKafka(partitions = 3
        , brokerProperties = {"listeners=PLAINTEXT://localhost:9092"}
        , ports = {9092}
        , topics = {"reservation"}
       )
class ReservationMessageTest {

    @Autowired
    private ReservationMessageProducer reservationMessageProducer;

    @Autowired
    private ReservationConsumer reservationConsumer;

    /**
     * Reservation Topic을 발행하고
     * reservation consumer group의 consumer 1이 잘 수신하는지 확인
     * @throws InterruptedException
     */
    @DisplayName("카프카 메시지 발행과 수신 정상 작동 확인 ")
    @Test
    void publish_getMessage() throws InterruptedException {
        //given
        ReservationEvent event = ReservationEvent.builder()
                .seatId(UUID.fromString("4c907722-5598-11ef-8e94-bba7136bde1a"))
                .userId(UUID.fromString("11ef5521-3033-e112-8e94-bba7136bde1a"))
                .confirmYn("Y").build();


        //when : 발행 확인
        reservationMessageProducer.publish(event);

        //then : 수신 확인

        KafkaMessage<ReservationEvent> receivedMessage = reservationConsumer.getRecords().poll(10,TimeUnit.SECONDS);
        assertThat(receivedMessage).isNotNull();
        assertThat(receivedMessage.getPayload()).isNotNull();

    }
}