package com.hhplus.concertReserv.application;

import com.hhplus.concertReserv.domain.token.dto.TokenDto;
import com.hhplus.concertReserv.exception.TokenNotFoundException;
import com.hhplus.concertReserv.domain.common.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Slf4j
class TokenIntegrationTest {

    @Autowired
    TokenFacade tokenFacade;

    // =================================================== 예외 확인 테스트 ===================================================
    @DisplayName("등록되지 않은 사용자 ID일 경우, IllegalArgumentException 처리")
    @Test
    void getToken_noMemberID() {
        // given
        // 테스트 데이터에 없는 memberId
        UUID memberID = UUID.fromString("8e9ab210-3f92-4824-895e-783623dd58a9");
        UUID concertId = UUID.fromString("2e50b778-4de9-40e1-b9ba-9b1e786b4197");
        TokenCommand.CreateToken request = new TokenCommand.CreateToken(memberID, concertId);

        // when
        assertThrows(IllegalArgumentException.class, () -> tokenFacade.getToken(request));
    }

    @DisplayName("등록되지 않은 콘서트 ID일 경우, IllegalArgumentException 처리")
    @Test
    void getToken_noConcertId() {
        // given
        // 테스트 데이터에 없는 memberId
        UUID memberID = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        UUID concertId = UUID.fromString("8e9ab210-3f92-4824-895e-783623dd58a9");

        TokenCommand.CreateToken request = new TokenCommand.CreateToken(memberID, concertId);

        // when
        assertThrows(IllegalArgumentException.class, () -> tokenFacade.getToken(request));
    }


    @DisplayName("등록되지 않은 토큰을 조회할 경우, TokenNotFoundException 처리")
    @Test
    void isToken_Fail() {
        // given
        UUID memberId = UUID.fromString("9687369c-3362-4d1b-bbba-667a8ef37ab8");
        UUID concertId = UUID.fromString("429de44f-c2a0-47f0-82ff-8feb6f748029");
        TokenCommand.CreateToken request = new TokenCommand.CreateToken(memberId, concertId);

        // Then 1: TokenNotFoundException 발생하는지 확인
        TokenNotFoundException exception = assertThrows(TokenNotFoundException.class, () -> tokenFacade.findActivateToken(request));

        // Then 2 : 에러 메시지 확인
        String message = exception.getMessage();
        assertEquals(ErrorCode.TOKEN_NOT_FOUND.getMessage(), message);
    }

    @DisplayName("대기열에 등록한 토큰이 아직 활성화되지 않았을 경우, TokenNotFoundException 처리 ")
    @Test
    void testMethodName() {
        // given : 대기열에 등록된 토큰 정보 가져오기

        UUID memberId1 = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        UUID concertId1 = UUID.fromString("2e50b778-4de9-40e1-b9ba-9b1e786b4197");
        TokenCommand.CreateToken request1 = new TokenCommand.CreateToken(memberId1, concertId1);
        tokenFacade.getToken(request1);


        // then
        TokenNotFoundException exception = assertThrows(TokenNotFoundException.class,()->tokenFacade.findActivateToken(request1));

        String message = exception.getMessage();
        assertEquals(ErrorCode.TOKEN_DEACTIVATED.getMessage(),message);

    }


    // =================================================== 정상 확인 테스트 ===================================================

    /**
     * 2명이 대기열 토큰 발급을 요청할 경우
     * Redis를 이용해 대기열 토큰 생성
     */
    @DisplayName("대기열 토큰 생성 정상 작동 확인 ")
    @Test
    void getToken() {
        // given
        UUID memberId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        UUID concertId = UUID.fromString("2e50b778-4de9-40e1-b9ba-9b1e786b4197");
        TokenCommand.CreateToken request = new TokenCommand.CreateToken(memberId, concertId);

        UUID memberId2 = UUID.fromString("9687369c-3362-4d1b-bbba-667a8ef37ab8");
        UUID concertId2 = UUID.fromString("2e50b778-4de9-40e1-b9ba-9b1e786b4197");
        TokenCommand.CreateToken request2 = new TokenCommand.CreateToken(memberId2, concertId2);

        // when
        TokenDto dto = tokenFacade.getToken(request);
        TokenDto dto2 = tokenFacade.getToken(request2);


        assertEquals(dto.getMemberId(), memberId);
        assertEquals(dto.getConcertId(), concertId);
        assertEquals(dto2.getMemberId(), memberId2);
        assertEquals(dto2.getConcertId(), concertId2);


    }

    /**
     * 1분마다 도는 활성화 스케줄러 기다려야함
     * @throws InterruptedException
     */

    @DisplayName("대기열에 등록한 토큰이 정상적으로 활성화 ")
    @Test
    void findActivateToken() throws InterruptedException {

        // given : 대기열에 등록된 토큰 정보 가져오기
        UUID memberId1 = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        UUID concertId1 = UUID.fromString("2e50b778-4de9-40e1-b9ba-9b1e786b4197");
        TokenCommand.CreateToken request1 = new TokenCommand.CreateToken(memberId1, concertId1);
        tokenFacade.getToken(request1);

        // when
        Thread.sleep(60000);
        boolean result = tokenFacade.findActivateToken(request1);

        //then
        assertEquals(true, result);
    }


}