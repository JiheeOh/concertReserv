package com.hhplus.concertReserv.domain.service;

import com.hhplus.concertReserv.domain.token.dto.TokenDto;
import com.hhplus.concertReserv.domain.token.entity.Token;
import com.hhplus.concertReserv.domain.token.repositories.TokenRepository;
import com.hhplus.concertReserv.exception.TokenNotFoundException;
import com.hhplus.concertReserv.domain.token.service.TokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {

    @InjectMocks
    private TokenService tokenService;

    @Mock
    private TokenRepository tokenRepository;

    /**
     * 대기열 등록 정상확인
     * 내 순서 이전에 대기열 있을 경우
     */
    @Test
    @DisplayName("다음 차례 대기열 등록")
    void createToken_whenWaitingExist() {
        //given
        UUID concertId = UUID.fromString("2e50b778-4de9-40e1-b9ba-9b1e786b4197");
        UUID memberId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");

        Token lastOne = new Token();

        Token resultToken = new Token();
        resultToken.setMemberId(memberId);
        resultToken.setConcertId(concertId);


        //when
        when(tokenRepository.getLastOne(concertId)).thenReturn(Optional.of(lastOne));

        //then
        assertThat(tokenService.createToken(memberId, concertId).getMemberId()).isEqualTo(resultToken.getMemberId());
        assertThat(tokenService.createToken(memberId, concertId).getConcertId()).isEqualTo(resultToken.getConcertId());
        assertThat(tokenService.createToken(memberId, concertId)).isInstanceOf(TokenDto.class);

    }

    /**
     * 대기열 등록 정상확인
     * 내 순서 이전에 대기열 없을경우
     */
    @Test
    @DisplayName("첫주자 대기열 등록")
    void createToken_whenWaiting_Not_Exist() {
        //given
        UUID concertId = UUID.fromString("2e50b778-4de9-40e1-b9ba-9b1e786b4197");
        UUID memberId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");

        Token lastOne = new Token();

        Token resultToken = new Token();
//        resultToken.setWaitOrder(0L);
//        resultToken.setStatus((1));
        resultToken.setMemberId(memberId);
        resultToken.setConcertId(concertId);


        //when
        when(tokenRepository.getLastOne(concertId)).thenReturn(Optional.of(lastOne));

        //then
//        assertThat(tokenService.createToken(memberId,concertId).getWaitOrder()).isEqualTo(resultToken.getWaitOrder());
//        assertThat(tokenService.createToken(memberId,concertId).getStatus()).isEqualTo(resultToken.getStatus());
        assertThat(tokenService.createToken(memberId,concertId).getMemberId()).isEqualTo(resultToken.getMemberId());
        assertThat(tokenService.createToken(memberId,concertId).getConcertId()).isEqualTo(resultToken.getConcertId());
        assertThat(tokenService.createToken(memberId,concertId)).isInstanceOf(TokenDto.class);


    }

    /**
     * 비활성화된 토큰을 조회할 경우
     * TokenNotFoundException 발생하는지 확인
     */
    @DisplayName("비활성화된 토큰을 조회할 경우 fail")
    @Test
    void find_deactivate_Token_fail() {
        //given
        UUID tokenId = UUID.randomUUID();

        when(tokenRepository.findActivateToken(tokenId)).thenReturn(Optional.empty());

        assertThrows(TokenNotFoundException.class,()-> tokenService.findActivateToken(tokenId));
    }
}