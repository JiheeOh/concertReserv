package com.hhplus.concertReserv.interfaces.event.reservation;

import com.hhplus.concertReserv.domain.reservation.event.PaymentEvent;
import com.hhplus.concertReserv.domain.sender.PaymentSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Slf4j
public class PaymentEventHandler {

    private final PaymentSender paymentSender;

    public PaymentEventHandler(PaymentSender paymentSender) {
        this.paymentSender = paymentSender;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void publish(PaymentEvent paymentEvent){
      paymentSender.sendInfo(paymentEvent);

    }
}
