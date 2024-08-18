package com.hhplus.concertReserv.interfaces.scheduler;

import com.hhplus.concertReserv.domain.reservation.message.OutboxEnum;
import com.hhplus.concertReserv.domain.reservation.message.entity.ReservationOutbox;
import com.hhplus.concertReserv.infrastructure.spring.reservation.outbox.ReservationOutboxJPARepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ReservationOutboxSchedulerTest {

    @Autowired
    ReservationOutboxScheduler outboxScheduler;

    @Autowired
    ReservationOutboxJPARepository jpaRepository;


    @DisplayName("Payment 메시지 스케줄 재발행 정상 확인 ")
    @Test
    void retryPublishKafkaMessage() throws InterruptedException {
        // given
        ReservationOutbox outbox = new ReservationOutbox();
        outbox.setRetry(0L);
        outbox.setUserId(UUID.randomUUID());
        outbox.setSeatId(UUID.randomUUID());
        outbox.setEventCreateDt(LocalDateTime.now().minusMinutes(30));
        outbox.setStatus(OutboxEnum.INIT.getStatus());

        //when
        outboxScheduler.retryPublishKafkaMessage();

        //then
        Thread.sleep(30000);
        Optional<ReservationOutbox> result =jpaRepository.findBySeatIdUserId(outbox.getSeatId(),outbox.getUserId());

        assertThat(result.get().getUserId()).isEqualTo(result.get().getUserId());
        assertThat(result.get().getSeatId()).isEqualTo(result.get().getSeatId());
        assertThat(result.get().getRetry()).isEqualTo(1);

    }


}