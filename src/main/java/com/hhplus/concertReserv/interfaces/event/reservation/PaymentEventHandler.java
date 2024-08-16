package com.hhplus.concertReserv.interfaces.event.reservation;

import com.hhplus.concertReserv.domain.reservation.event.PaymentEvent;
import com.hhplus.concertReserv.domain.reservation.kafka.PaymentMessagePublisher;
import com.hhplus.concertReserv.domain.reservation.kafka.outbox.PaymentOutboxWriter;
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

    private final PaymentOutboxWriter outboxWriter;


    public PaymentEventHandler(PaymentMessagePublisher paymentMessagePublisher, PaymentOutboxWriter outboxWriter) {
        this.paymentMessagePublisher = paymentMessagePublisher;
        this.outboxWriter = outboxWriter;
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


    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void saveOutbox(PaymentEvent paymentEvent){
        outboxWriter.save(paymentEvent);

    }
}
