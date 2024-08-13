package com.hhplus.concertReserv.domain.service;

import com.hhplus.concertReserv.domain.token.repositories.TokenRepository;
import com.hhplus.concertReserv.domain.token.service.TokenService;
import com.hhplus.concertReserv.exception.TokenNotFoundException;
import com.hhplus.concertReserv.domain.common.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {

    @InjectMocks
    private TokenService tokenService;

    @Mock
    private TokenRepository tokenRepository;


    /**
     * 비활성화된 토큰을 조회할 경우
     * TokenNotFoundException 발생하는지 확인
     */
    @DisplayName("비활성화된 토큰을 조회할 경우 fail")
    @Test
    void find_deactivate_Token_fail() {
        //given
        UUID memberId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        UUID concertId = UUID.fromString("2e50b778-4de9-40e1-b9ba-9b1e786b4197");


        TokenNotFoundException exception = assertThrows(TokenNotFoundException.class,()-> tokenService.findActivateToken(memberId,concertId));
        String message = exception.getMessage();

        assertEquals(message, ErrorCode.TOKEN_DEACTIVATED.getMessage());
    }




}