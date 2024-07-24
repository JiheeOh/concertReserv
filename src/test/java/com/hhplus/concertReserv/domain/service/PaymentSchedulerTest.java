package com.hhplus.concertReserv.domain.service;

import com.hhplus.concertReserv.domain.concert.repositories.SeatRepository;
import com.hhplus.concertReserv.domain.reservation.repositories.PaymentRepository;
import com.hhplus.concertReserv.domain.reservation.repositories.ReservationRepository;
import com.hhplus.concertReserv.domain.token.repositories.TokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class PaymentSchedulerTest {

    @Autowired
    SeatRepository seatRepository;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    TokenRepository tokenRepository;

    /**
     * 스케줄러로 결제 시간 만료 처리하는 기능 테스트
     * 1. 결제 마감 시간이 지난 결제Id 조회
     * 2. 결제 ID에 해당되는 token id 만료 처리
     * 3. 자리 반납 처리
     * 4. 예약 반납 처리
     */
    @DisplayName("결제 시간 만료 처리 정상확인 ")
    @Test
    void testMethodName() {
        // given

        // when

        //then

    }


}
