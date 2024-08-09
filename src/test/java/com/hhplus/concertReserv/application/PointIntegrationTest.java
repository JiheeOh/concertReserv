package com.hhplus.concertReserv.application;

import com.hhplus.concertReserv.domain.member.dto.PointDto;
import com.hhplus.concertReserv.domain.reservation.dto.ReserveDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@Slf4j
class PointIntegrationTest {

    @Autowired
    PointFacade pointFacade;

    @Autowired
    ReserveFacade reserveFacade;




    // =================================================== 정상 확인 테스트 ===================================================

    @DisplayName("충전 가능 정상확인 ")
    @Test
    void charge() {
        // given
        UUID memberID = UUID.fromString("11ef5521-3033-e112-8e94-bba7136bde1a");
        Long amount = 10000L;
        PointCommand.Charge request = new PointCommand.Charge(memberID, amount);

        // when
        // 기존 포인트
        PointDto pointDto = pointFacade.getPoint(memberID);
        // 충전 후
        PointDto result = pointFacade.charge(request);

        //then
        assertThat(result.getResult()).isEqualTo(true);
        assertThat(result.getPoint()).isEqualTo(pointDto.getPoint() + amount);

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
        assertDoesNotThrow(() -> pointFacade.paid(request));

    }

    @DisplayName("포인트 조회 정상확인")
    @Test
    void getPoint() {
        UUID memberID = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        assertDoesNotThrow(() -> pointFacade.getPoint(memberID));
    }

    @DisplayName("포인트 조회 세부정보 확인")
    @Test
    void getPoint_detail() {
        UUID memberID = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        PointDto result = pointFacade.getPoint(memberID);
        log.info(result.toString());

    }
}