package com.hhplus.concertReserv.interfaces.event.reservation;

import com.hhplus.concertReserv.domain.reservation.event.PaymentEvent;
import com.hhplus.concertReserv.domain.reservation.kafka.PaymentMessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Slf4j
public class PaymentEventHandler {

//    private final PaymentSender paymentSender;

    private final PaymentMessagePublisher paymentMessagePublisher;


    public PaymentEventHandler(PaymentMessagePublisher paymentMessagePublisher) {
        this.paymentMessagePublisher = paymentMessagePublisher;
    }

//    @Async
//    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
//    public void publish(PaymentEvent paymentEvent){
//      paymentSender.sendInfo(paymentEvent);
//
//    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void publishMessage(PaymentEvent paymentEvent){
        paymentMessagePublisher.publish(paymentEvent);

    }
}
