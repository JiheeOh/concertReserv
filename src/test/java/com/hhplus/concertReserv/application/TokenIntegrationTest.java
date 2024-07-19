package com.hhplus.concertReserv.application;

import com.hhplus.concertReserv.domain.token.dto.TokenDto;
import com.hhplus.concertReserv.exception.TokenNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Slf4j
class TokenIntegrationTest {

    @Autowired
    TokenFacade tokenFacade;

    // =================================================== 예외 확인 테스트 ===================================================
    @DisplayName("등록되지 않은 사용자 ID일 경우, IllegalArgumentException 처리")
    @Test
    void getToken_noMemberID () {
        // given
        // 테스트 데이터에 없는 memberId
        UUID memberID = UUID.fromString("8e9ab210-3f92-4824-895e-783623dd58a9");
        UUID concertId = UUID.fromString("2e50b778-4de9-40e1-b9ba-9b1e786b4197");
        TokenCommand.CreateToken request = new TokenCommand.CreateToken(memberID, concertId);

        // when
        assertThrows(IllegalArgumentException.class,()->tokenFacade.getToken(request));
    }

    @DisplayName("등록되지 않은 콘서트 ID일 경우, IllegalArgumentException 처리")
    @Test
    void getToken_noConcertId  () {
        // given
        // 테스트 데이터에 없는 memberId
        UUID memberID = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        UUID concertId = UUID.fromString("8e9ab210-3f92-4824-895e-783623dd58a9");

        TokenCommand.CreateToken request = new TokenCommand.CreateToken(memberID, concertId);

        // when
        assertThrows(IllegalArgumentException.class,()->tokenFacade.getToken(request));
    }


    // =================================================== 정상 확인 테스트 ===================================================

    /**
     * 1명이 대기열 토큰 발급을 요청할 경우
     */
    @DisplayName("대기열 토큰 생성 정상 작동 확인 ")
    @Test
    void getToken() {
        // given
        UUID memberID = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        UUID concertId = UUID.fromString("2e50b778-4de9-40e1-b9ba-9b1e786b4197");
        TokenCommand.CreateToken request = new TokenCommand.CreateToken(memberID, concertId);

        // when
        TokenDto dto = tokenFacade.getToken(request);

        assertThat(dto.getStatus()).isEqualTo(1);
        log.info(dto.toString());

    }

    @DisplayName("해당 토큰이 등록된 후 스케줄러가 안돌 경우, TokenNotFoundException 발생하는지 확인 ")
    @Test
    void findActivateToken() {
        // given
        UUID memberID = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        UUID concertId = UUID.fromString("2e50b778-4de9-40e1-b9ba-9b1e786b4197");
        TokenCommand.CreateToken request = new TokenCommand.CreateToken(memberID, concertId);
        TokenDto dto = tokenFacade.getToken(request);

        assertThrows(TokenNotFoundException.class, () -> tokenFacade.findActivateToken(dto.getTokenId()));
    }
}