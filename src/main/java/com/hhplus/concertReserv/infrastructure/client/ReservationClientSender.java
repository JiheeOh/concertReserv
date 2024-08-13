package com.hhplus.concertReserv.infrastructure.client;

import com.hhplus.concertReserv.domain.reservation.event.ReservationEvent;
import com.hhplus.concertReserv.domain.sender.ReservationSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReservationClientSender implements ReservationSender {

    public void sendInfo(ReservationEvent reservationEvent){
        try{
            //TODO : 예약 정보 전송 로직 구현
            log.info("예약 정보 전송 로직 실행 ");
        }catch (Exception e){
            log.error("예약 정보 전송 실패 ");
        }
    }
}
