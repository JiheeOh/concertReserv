package com.hhplus.concertReserv.application;

import com.hhplus.concertReserv.domain.concert.SeatEnum;
import com.hhplus.concertReserv.domain.reservation.dto.ReserveDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@Slf4j
class ReserveIntegrationTest {
    /**
     * 콘서트 예약 통합 테스트
     *  - 해당 콘서트의 예약가능한 날짜, 좌석 조회 기능
     *  - 콘서트 선착순 예약 기능
     * Facade 부터 DB까지 통합 테스트 진행
     */

    @Autowired
    ReserveFacade reserveFacade;

    @Autowired
    PaymentFacade paymentFacade;

    // =================================================== 예외 확인 테스트 ===================================================


    // =================================================== 정상 확인 테스트 ===================================================
    @DisplayName("예약가능한 좌석 조회 기능 정상 확인")
    @Test
    void findReserveAvailableSeat() {
        // given
        UUID concertId = UUID.fromString("2e50b778-4de9-40e1-b9ba-9b1e786b4197");
        LocalDateTime concertDt = LocalDateTime.of(2024,12,25,13,0,0,0);

        //then
        assertDoesNotThrow(() -> reserveFacade.findReserveAvailableSeat(concertId,concertDt));
        assertThat(reserveFacade.findReserveAvailableSeat(concertId,concertDt).getResult()).isEqualTo(true);


    }

    @DisplayName("예약이 다 차서 예약이 불가능한 콘서트 캐싱 삭제 정상 확인 ")
    @Test
    void findReserveAvailableSeat_fullyBooked(){
        // given
        UUID concertID = UUID.fromString("0d511ce8-f6cc-4c4d-9d50-11725895ec11");
        LocalDateTime concertDt = LocalDateTime.of(2024,11,12,13,0,0,0);


        // when
        reserveFacade.findReserveAvailableSeat(concertID,concertDt);


        //then

    }




    @DisplayName("예약가능한 날짜 조회 기능 정상 확인")
    @Test
    void findReserveAvailableSchedule() {
        // given
//        UUID concertId = UUID.fromString("2e50b778-4de9-40e1-b9ba-9b1e786b4197");
        UUID concertId = UUID.fromString("0d511ce8-f6cc-4c4d-9d50-11725895ec11");


        //then
        long beforeTime = System.currentTimeMillis();
        log.info(" === findReserveAvailableSchedule start === ");
        reserveFacade.findReserveAvailableSchedule(concertId);
        long afterTime = System.currentTimeMillis();
        long diffTime = (afterTime-beforeTime)/1000;
        log.info(String.format(" === duration time %d === ",diffTime));



    }

    /**
     * 1명이 콘서트의 좌석을 예약할 경우
     * 1. 결제 ID를 받는지 확인
     * 2. 결제 만료 시간이 5분뒤로 정해지는지 확인
     * ( 요청보내기 전 +5분, 요청보내고난 후 +5분 사이인지 확인 : 이거보다 좋은 확인 방법이 있는지는 탐구해볼 것)
     */
    @DisplayName("좌석 예약 기능 정상 확인 : 1 명 기준 ")
    @Test
    void applySeat_One() {
        //given
        UUID memberID = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        UUID seatId = UUID.fromString("280a8a4d-a27f-4d01-b031-2a003cc4c039");
        ReserveCommand.ApplySeat request = new ReserveCommand.ApplySeat(memberID, seatId);

        //when
        LocalDateTime beforeTime = LocalDateTime.now();
        ReserveDto result = reserveFacade.applySeat(request);
        LocalDateTime afterTime = LocalDateTime.now();

        // then
        assertThat(result.getResult()).isEqualTo(true);
        // 결제 ID 확인
        assertThat(result.getPayment().getPayId()).isNotEqualTo(null);
        // 결제 만료 시간 5분 뒤인지 확인
        assertThat(result.getPayment().getDueTime().getMinute()).isBetween(beforeTime.plusMinutes(5).getMinute(), afterTime.plusMinutes(5).getMinute());

        // 자리가 OCCUPIED 되었는지 확인
        assertThat(result.getConcert().getSeat().get(0).getStatus()).isEqualTo(SeatEnum.RESERVED.getStatus());
    }


    @DisplayName("완납 결제 정상확인")
    @Test
    void paid() {
        //given 1 : 예약 내역 생성
        UUID memberID = UUID.fromString("11ef5521-3033-e112-8e94-bba7136bde1a");
        UUID seatID = UUID.fromString("11ef557f-6745-0e20-8e94-bba7136bde1a");

        ReserveCommand.ApplySeat reserveRequest = new ReserveCommand.ApplySeat(memberID, seatID);
        ReserveDto reserveResult = reserveFacade.applySeat(reserveRequest);


        // given2 : 결제 요청
        PointCommand.Paid request = new PointCommand.Paid(reserveResult.getPayment().getPayId(), reserveResult.getPayment().getPayAmount());

        //when
        assertDoesNotThrow(() -> paymentFacade.paid(request));

    }
}