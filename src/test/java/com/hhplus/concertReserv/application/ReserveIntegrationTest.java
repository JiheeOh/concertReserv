package com.hhplus.concertReserv.application;

import com.hhplus.concertReserv.domain.concert.SeatEnum;
import com.hhplus.concertReserv.domain.reservation.dto.ReserveDto;
import com.hhplus.concertReserv.interfaces.presentation.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

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

    // =================================================== 예외 확인 테스트 ===================================================


    // =================================================== 정상 확인 테스트 ===================================================
    @DisplayName("예약가능한 날짜 좌석 조회 기능 정상 확인")
    @Test
    void findReserveAvailable() {
        // given
        UUID concertId = UUID.fromString("2e50b778-4de9-40e1-b9ba-9b1e786b4197");

        //then
        assertDoesNotThrow(() -> reserveFacade.findReserveAvailable(concertId));
        assertThat(reserveFacade.findReserveAvailable(concertId).getResult()).isEqualTo(true);


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

    /**
     * 여러명이 같은 좌석을 예약할 경우,
     * 선착순 1명만 해당 좌석을 예약되고
     * 나머지는 OccupiedException 을 받는지 확인
     */

    @DisplayName("좌석 예약 기능 정상 확인 : 동시성 테스트")
    @Test
    void applySeat_Multiple() throws ExecutionException, InterruptedException {
        //given
        UUID member1 = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        UUID member2 = UUID.fromString("9687369c-3362-4d1b-bbba-667a8ef37ab8");
        UUID member3 = UUID.fromString("411bd85c-c387-4c65-ab17-196a5a6f1505");

        UUID seatId = UUID.fromString("280a8a4d-a27f-4d01-b031-2a003cc4c039");

        ReserveCommand.ApplySeat request1 = new ReserveCommand.ApplySeat(member1, seatId);
        ReserveCommand.ApplySeat request2 = new ReserveCommand.ApplySeat(member2, seatId);
        ReserveCommand.ApplySeat request3 = new ReserveCommand.ApplySeat(member3, seatId);

        // when
        CompletableFuture<ReserveDto> future1  = CompletableFuture.supplyAsync(() -> reserveFacade.applySeat(request1));
        CompletableFuture<ReserveDto> future2 = CompletableFuture.supplyAsync(() -> reserveFacade.applySeat(request2));
        CompletableFuture<ReserveDto> future3 = CompletableFuture.supplyAsync(() -> reserveFacade.applySeat(request3));

        List<CompletableFuture<ReserveDto>> futures = List.of(future1, future2, future3);

        CompletableFuture<List<ReserveDto>> result = CompletableFuture.allOf(futures.toArray((new CompletableFuture[futures.size()])))
                .thenApply(v -> futures.stream().map(CompletableFuture::join).collect(Collectors.toList()));

        //then
        // 성공한 갯수가 1개인지 확인
        assertThat(result.get().stream().filter(ReserveDto::getResult).toList().size()).isEqualTo(1);
        // 실패 갯수가 2개인지 확인
        assertThat(result.get().stream().filter(x->!x.getResult()).toList().size()).isEqualTo(2);
        // 예외 메세지 확인
        assertThat(result.get().stream().filter(x->!x.getResult()).toList().get(0).getMessage()).isEqualTo(ErrorCode.OCCUPIED_SEAT.getMessage());

    }


}