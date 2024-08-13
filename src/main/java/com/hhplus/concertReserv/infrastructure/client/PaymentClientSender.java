package com.hhplus.concertReserv.infrastructure.client;

import com.hhplus.concertReserv.domain.reservation.event.PaymentEvent;
import com.hhplus.concertReserv.domain.sender.PaymentSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PaymentClientSender implements PaymentSender {


    public void sendInfo(PaymentEvent paymentEvent){
        try{
            //TODO : 결제 정보 전송 로직 구현
            log.info("결제 정보 전송 로직 실행 ");
        }catch (Exception e){
            log.error("결제 정보 전송 실패 ");
        }
    }
}
