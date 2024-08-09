package com.hhplus.concertReserv.interfaces.presentation;

import com.hhplus.concertReserv.interfaces.externalApi.ReservationApiClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Slf4j
public class ReservationEventHandler {

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void publish(ReservationEvent reservationEvent){
        try{
            ReservationApiClient reservationApiClient = new ReservationApiClient();
            reservationApiClient.sendReservationResult(reservationEvent);

        }catch (Exception e){
            log.error("예약 정보처리에 실패했습니다");
        }
    }
}
