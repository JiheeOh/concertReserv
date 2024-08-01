package com.hhplus.concertReserv.application;

import com.hhplus.concertReserv.domain.reservation.dto.ReserveDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.transaction.UnexpectedRollbackException;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Slf4j
public class ReserveConcurrencyTest {

    @Autowired
    ReserveFacade reserveFacade;


    /**
     * 여러명이 같은 좌석을 예약할 경우,
     * 선착순 1명만 해당 좌석을 예약되고
     * 나머지는 OccupiedException 을 받고
     * 트랜잭션 rollback이 되는지 확인
     */

    @DisplayName("좌석 예약 동시성 테스트 : 유니크 키 ")
    @Test
    void applySeat_Multiple_UniqueKey() {
        //given
        UUID member1 = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        UUID member2 = UUID.fromString("9687369c-3362-4d1b-bbba-667a8ef37ab8");
        UUID member3 = UUID.fromString("411bd85c-c387-4c65-ab17-196a5a6f1505");

        UUID seatId = UUID.fromString("280a8a4d-a27f-4d01-b031-2a003cc4c039");



        ReserveCommand.ApplySeat request1 = new ReserveCommand.ApplySeat(member1, seatId);
        ReserveCommand.ApplySeat request2 = new ReserveCommand.ApplySeat(member2, seatId);
        ReserveCommand.ApplySeat request3 = new ReserveCommand.ApplySeat(member3, seatId);

        // when
        CompletableFuture<ReserveDto> future1 = CompletableFuture.supplyAsync(() -> reserveFacade.applySeat(request1));
        CompletableFuture<ReserveDto> future2 = CompletableFuture.supplyAsync(() -> reserveFacade.applySeat(request2));
        CompletableFuture<ReserveDto> future3 = CompletableFuture.supplyAsync(() -> reserveFacade.applySeat(request3));

        List<CompletableFuture<ReserveDto>> futures = List.of(future1, future2, future3);

        CompletableFuture<List<ReserveDto>> result = CompletableFuture.allOf(futures.toArray((new CompletableFuture[futures.size()])))
                .thenApply(v -> futures.stream().map(CompletableFuture::join).collect(Collectors.toList()));

        Exception exception = new Exception();

        try {
            result.get().get(0);
            result.get().get(1);
            result.get().get(2);
        } catch (Exception e) {
            exception = (Exception) e.getCause();
        }
        assertTrue(exception instanceof UnexpectedRollbackException);
    }


    @DisplayName("좌석 예약 동시성 테스트 : 낙관적 락 ")
    @Test
    void applySeat_Multiple_Optimistic() {

        //given
        UUID member1 = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        UUID member2 = UUID.fromString("9687369c-3362-4d1b-bbba-667a8ef37ab8");
        UUID member3 = UUID.fromString("411bd85c-c387-4c65-ab17-196a5a6f1505");

        UUID seatId = UUID.fromString("280a8a4d-a27f-4d01-b031-2a003cc4c039");


        ReserveCommand.ApplySeat request1 = new ReserveCommand.ApplySeat(member1, seatId);
        ReserveCommand.ApplySeat request2 = new ReserveCommand.ApplySeat(member2, seatId);
        ReserveCommand.ApplySeat request3 = new ReserveCommand.ApplySeat(member3, seatId);

        // when
        CompletableFuture<ReserveDto> future1 = CompletableFuture.supplyAsync(() -> reserveFacade.applySeat(request1));
        CompletableFuture<ReserveDto> future2 = CompletableFuture.supplyAsync(() -> reserveFacade.applySeat(request2));
        CompletableFuture<ReserveDto> future3 = CompletableFuture.supplyAsync(() -> reserveFacade.applySeat(request3));

        List<CompletableFuture<ReserveDto>> futures = List.of(future1, future2, future3);

        CompletableFuture<List<ReserveDto>> result = CompletableFuture.allOf(futures.toArray((new CompletableFuture[futures.size()])))
                .thenApply(v -> futures.stream().map(CompletableFuture::join).collect(Collectors.toList()));


        Exception exception = new Exception();

        try {
            result.get();
        } catch (Exception e) {
            exception = (Exception) e.getCause();
        }

        assertTrue(exception instanceof UnexpectedRollbackException);
    }

    @DisplayName("좌석 예약 동시성 테스트 : 비관적 락 ")
    @Test
    void applySeat_Multiple_Pessimistic() throws ExecutionException, InterruptedException {

        //given
        UUID member1 = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        UUID member2 = UUID.fromString("9687369c-3362-4d1b-bbba-667a8ef37ab8");
        UUID member3 = UUID.fromString("411bd85c-c387-4c65-ab17-196a5a6f1505");

        UUID seatId = UUID.fromString("280a8a4d-a27f-4d01-b031-2a003cc4c039");


        ReserveCommand.ApplySeat request1 = new ReserveCommand.ApplySeat(member1, seatId);
        ReserveCommand.ApplySeat request2 = new ReserveCommand.ApplySeat(member2, seatId);
        ReserveCommand.ApplySeat request3 = new ReserveCommand.ApplySeat(member3, seatId);

        // when
        CompletableFuture<ReserveDto> future1 = CompletableFuture.supplyAsync(() -> reserveFacade.applySeat(request1));
        CompletableFuture<ReserveDto> future2 = CompletableFuture.supplyAsync(() -> reserveFacade.applySeat(request2));
        CompletableFuture<ReserveDto> future3 = CompletableFuture.supplyAsync(() -> reserveFacade.applySeat(request3));

        List<CompletableFuture<ReserveDto>> futures = List.of(future1, future2, future3);

        CompletableFuture<List<ReserveDto>> result = CompletableFuture.allOf(futures.toArray((new CompletableFuture[futures.size()])))
                .thenApply(v -> futures.stream().map(CompletableFuture::join).collect(Collectors.toList()));

        assertThat(result.get().stream().filter(ReserveDto::getResult).toList().size()).isEqualTo(1);
        assertThat(result.get().stream().filter(x -> !x.getResult()).toList().size()).isEqualTo(2);
    }
}
