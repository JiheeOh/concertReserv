package com.hhplus.concertReserv.application;

import com.hhplus.concertReserv.domain.concert.dto.ConcertDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@Slf4j
class ConcertIntegrationTest {
    /**
     * 콘서트 통합테스트
     *  - 콘서트 목록 조회
     * Facade 부터 DB까지 통합 테스트 진행
     */

    @Autowired
    ConcertFacade concertFacade;

    // =================================================== 예외 확인 테스트 ===================================================


    // =================================================== 정상 확인 테스트 ===================================================
    @DisplayName("콘서트 목록 조회 정상 확인")
    @Test
    void findConcertList() {
        assertDoesNotThrow(()->concertFacade.findConcertList());
    }

    @DisplayName("콘서트 목록 조회 내역 확인")
    @Test
    void findConcertList_detail() {
        List<ConcertDto> result = concertFacade.findConcertList();
        for (ConcertDto dto : result){
            log.info(dto.toString());
        }
    }
}