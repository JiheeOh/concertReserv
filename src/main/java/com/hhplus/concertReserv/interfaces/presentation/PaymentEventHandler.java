package com.hhplus.concertReserv.interfaces.presentation;

import com.hhplus.concertReserv.interfaces.externalApi.PaymentApiClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Slf4j
public class PaymentEventHandler {

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void publish(PaymentEvent paymentEvent){
        try{
            PaymentApiClient paymentApiClient = new PaymentApiClient();
            paymentApiClient.sendPaymentResult(paymentEvent);
        }catch(Exception e){
            log.error("결제 정보처리에 실패했습니다");
        }

    }
}
