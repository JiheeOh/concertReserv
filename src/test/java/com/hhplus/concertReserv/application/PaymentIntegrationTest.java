package com.hhplus.concertReserv.application;

import com.hhplus.concertReserv.domain.reservation.kafka.outbox.OutboxEnum;
import com.hhplus.concertReserv.domain.reservation.kafka.outbox.PaymentOutboxRepository;
import com.hhplus.concertReserv.domain.reservation.kafka.outbox.entity.PaymentOutbox;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PaymentIntegrationTest {

   @Autowired
    PaymentFacade paymentFacade;

   @Autowired
    PaymentOutboxRepository outboxRepository;


    /**
     * 결제 완료 후 outbox 에 결제 정보가 저장되는지 확인
     */
   @DisplayName("payment_outbox_test")
   @Test
   void paymet_outbox_test() throws InterruptedException {
       // given
       UUID paymentId = UUID.fromString("383c60bc-abd8-4100-8d58-75d1117632bd");
       Long amount = 57425L;

       PointCommand.Paid request = new PointCommand.Paid(paymentId,amount);

       // when

       paymentFacade.paid(request);

       //then
       Thread.sleep(120000);
       Optional<PaymentOutbox> result= outboxRepository.findByPaymentId(paymentId);

       assertThat(result.isPresent()).isEqualTo(true);
       assertThat(result.get().getPaymentId()).isEqualTo(paymentId);
       assertThat(result.get().getStatus()).isEqualTo(OutboxEnum.COMPLETED.getStatus());

   }



}
