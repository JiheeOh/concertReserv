package com.hhplus.concertReserv.application;

import com.hhplus.concertReserv.domain.member.dto.PointDto;
import com.hhplus.concertReserv.domain.reservation.dto.ReserveDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PointConcurrencyTest {

    @Autowired
    PointFacade pointFacade;

    @Autowired
    ReserveFacade reserveFacade;


    @DisplayName("포인트 동시성 테스트 : 락 없을 경우 순서 보장 fail ")
    @Test
    void point_Multiple_fail() {

        // given 1 : 예약 내역 생성
        UUID memberID = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        UUID seatID = UUID.fromString("280a8a4d-a27f-4d01-b031-2a003cc4c039");
        Long tokenId = 1L;

        ReserveCommand.ApplySeat reserveRequest = new ReserveCommand.ApplySeat(memberID, seatID, tokenId);
        ReserveDto reserveResult = reserveFacade.applySeat(reserveRequest);

        // given 2 : 포인트 사용/ 적립 금액
        Long payAmount = 100L;
        long chargeAmount = 200L;
        long payRestAmount = reserveResult.getPayment().getPayAmount()-payAmount;
        long chargeAmount2 = 300L;
        Long expectedPoint = pointFacade.getPoint(memberID).getPoint()-payAmount+chargeAmount-payRestAmount+chargeAmount2;

        PointCommand.Paid paidRequest1 = new PointCommand.Paid(tokenId, reserveResult.getPayment().getPayId(), payAmount);
        PointCommand.Charge chargeRequest1 = new PointCommand.Charge(memberID,chargeAmount);
        PointCommand.Paid paidRequest2 = new PointCommand.Paid(tokenId, reserveResult.getPayment().getPayId(), payRestAmount);
        PointCommand.Charge chargeRequest2 = new PointCommand.Charge(memberID,chargeAmount2);

        // when
        CompletableFuture<PointDto> future1 = CompletableFuture.supplyAsync(() -> pointFacade.paid(paidRequest1));
        CompletableFuture<PointDto> future2 = CompletableFuture.supplyAsync(() -> pointFacade.charge(chargeRequest1));
        CompletableFuture<PointDto> future3 = CompletableFuture.supplyAsync(() -> pointFacade.paid(paidRequest2));
        CompletableFuture<PointDto> future4 = CompletableFuture.supplyAsync(() -> pointFacade.charge(chargeRequest2));

        List<CompletableFuture<PointDto>> futures = List.of(future1, future2, future3,future4);

        CompletableFuture.allOf(futures.toArray((new CompletableFuture[futures.size()])))
                .thenApply(v -> futures.stream().map(CompletableFuture::join).collect(Collectors.toList()));

        // then
        assertThat(pointFacade.getPoint(memberID).getPoint()).isNotEqualTo(expectedPoint);
    }

    @DisplayName("포인트 동시성 테스트 : 낙관적 락 ")
    @Test
    void point_Multiple_Optimistic() {

        // given 1 : 예약 내역 생성
        UUID memberID = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        UUID seatID = UUID.fromString("280a8a4d-a27f-4d01-b031-2a003cc4c039");
        Long tokenId = 1L;

        ReserveCommand.ApplySeat reserveRequest = new ReserveCommand.ApplySeat(memberID, seatID, tokenId);
        ReserveDto reserveResult = reserveFacade.applySeat(reserveRequest);

        // given 2 : 포인트 사용/ 적립 금액
        Long payAmount = 100L;
        long chargeAmount = 200L;
        long payRestAmount = reserveResult.getPayment().getPayAmount()-payAmount;
        long chargeAmount2 = 300L;
        Long expectedPoint = pointFacade.getPoint(memberID).getPoint()-payAmount+chargeAmount-payRestAmount+chargeAmount2;

        PointCommand.Paid paidRequest1 = new PointCommand.Paid(tokenId, reserveResult.getPayment().getPayId(), payAmount);
        PointCommand.Charge chargeRequest1 = new PointCommand.Charge(memberID,chargeAmount);
        PointCommand.Paid paidRequest2 = new PointCommand.Paid(tokenId, reserveResult.getPayment().getPayId(), payRestAmount);
        PointCommand.Charge chargeRequest2 = new PointCommand.Charge(memberID,chargeAmount2);

        // when
        CompletableFuture<PointDto> future1 = CompletableFuture.supplyAsync(() -> pointFacade.paid(paidRequest1));
        CompletableFuture<PointDto> future2 = CompletableFuture.supplyAsync(() -> pointFacade.charge(chargeRequest1));
        CompletableFuture<PointDto> future3 = CompletableFuture.supplyAsync(() -> pointFacade.paid(paidRequest2));
        CompletableFuture<PointDto> future4 = CompletableFuture.supplyAsync(() -> pointFacade.charge(chargeRequest2));

        List<CompletableFuture<PointDto>> futures = List.of(future1, future2, future3,future4);

        CompletableFuture<List<PointDto>> result = CompletableFuture.allOf(futures.toArray((new CompletableFuture[futures.size()])))
                .thenApply(v -> futures.stream().map(CompletableFuture::join).collect(Collectors.toList()));

        // then
        assertThat(pointFacade.getPoint(memberID).getPoint()).isNotEqualTo(expectedPoint);
    }

    @DisplayName("좌석 예약 동시성 테스트 : 비관적 락 ")
    @Test
    void point_Multiple_Pessimistic() throws ExecutionException, InterruptedException {

        // given 1 : 예약 내역 생성
        UUID memberID = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        UUID seatID = UUID.fromString("280a8a4d-a27f-4d01-b031-2a003cc4c039");
        Long tokenId = 1L;

        ReserveCommand.ApplySeat reserveRequest = new ReserveCommand.ApplySeat(memberID, seatID, tokenId);
        ReserveDto reserveResult = reserveFacade.applySeat(reserveRequest);

        // given 2 : 포인트 사용/ 적립 금액
        Long payAmount = 100L;
        long chargeAmount = 200L;
        long payRestAmount = reserveResult.getPayment().getPayAmount()-payAmount;
        long chargeAmount2 = 300L;
        Long expectedPoint = pointFacade.getPoint(memberID).getPoint()-payAmount+chargeAmount-payRestAmount+chargeAmount2;

        PointCommand.Paid paidRequest1 = new PointCommand.Paid(tokenId, reserveResult.getPayment().getPayId(), payAmount);
        PointCommand.Charge chargeRequest1 = new PointCommand.Charge(memberID,chargeAmount);
        PointCommand.Paid paidRequest2 = new PointCommand.Paid(tokenId, reserveResult.getPayment().getPayId(), payRestAmount);
        PointCommand.Charge chargeRequest2 = new PointCommand.Charge(memberID,chargeAmount2);

        // when
        CompletableFuture<PointDto> future1 = CompletableFuture.supplyAsync(() -> pointFacade.paid(paidRequest1));
        CompletableFuture<PointDto> future2 = CompletableFuture.supplyAsync(() -> pointFacade.charge(chargeRequest1));
        CompletableFuture<PointDto> future3 = CompletableFuture.supplyAsync(() -> pointFacade.paid(paidRequest2));
        CompletableFuture<PointDto> future4 = CompletableFuture.supplyAsync(() -> pointFacade.charge(chargeRequest2));

        List<CompletableFuture<PointDto>> futures = List.of(future1, future2, future3,future4);

        CompletableFuture<List<PointDto>> result = CompletableFuture.allOf(futures.toArray((new CompletableFuture[futures.size()])))
                .thenApply(v -> futures.stream().map(CompletableFuture::join).collect(Collectors.toList()));

        // then
        assertThat(pointFacade.getPoint(memberID).getPoint()).isNotEqualTo(expectedPoint);
    }

}
