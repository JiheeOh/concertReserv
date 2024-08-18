package com.hhplus.concertReserv.interfaces.scheduler;

import com.hhplus.concertReserv.domain.reservation.message.OutboxEnum;
import com.hhplus.concertReserv.domain.reservation.message.entity.PaymentOutbox;
import com.hhplus.concertReserv.infrastructure.spring.reservation.outbox.PaymentOutboxJPARepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PaymentOutboxSchedulerTest {

    @Autowired
    PaymentOutboxScheduler outboxScheduler;

    @Autowired
    PaymentOutboxJPARepository jpaRepository;


    @DisplayName("Payment 메시지 스케줄 재발행 정상 확인 ")
    @Test
    void retryPublishKafkaMessage() throws InterruptedException {
        // given
        PaymentOutbox outbox = new PaymentOutbox();
        outbox.setRetry(0L);
        outbox.setPaymentId(UUID.randomUUID());
        outbox.setEventCreateDt(LocalDateTime.now().minusMinutes(30));
        outbox.setStatus(OutboxEnum.INIT.getStatus());

        //when
        outboxScheduler.retryPublishKafkaMessage();

        //then
        Thread.sleep(30000);
        Optional<PaymentOutbox> result = jpaRepository.findByPaymentId(outbox.getPaymentId());

        assertThat(result.get().getPaymentId()).isEqualTo(outbox.getPaymentId());
        assertThat(result.get().getRetry()).isEqualTo(1);
        assertThat(result.get().getStatus()).isEqualTo(OutboxEnum.COMPLETED.getStatus());


    }


}