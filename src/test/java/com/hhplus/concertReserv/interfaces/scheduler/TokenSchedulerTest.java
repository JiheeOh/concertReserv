package com.hhplus.concertReserv.interfaces.scheduler;

import com.hhplus.concertReserv.application.TokenCommand;
import com.hhplus.concertReserv.application.TokenFacade;
import com.hhplus.concertReserv.domain.concert.repositories.SeatRepository;
import com.hhplus.concertReserv.domain.token.dto.TokenDto;
import com.hhplus.concertReserv.domain.token.entity.Token;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class TokenSchedulerTest {

    @Autowired
    TokenScheduler tokenScheduler;

    @Autowired
    TokenFacade tokenFacade;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    private Token TOKEN_1;

    private Token TOKEN_2;

    private final String ACTIVATE_KEY= "activateToken";

    @BeforeEach
    void setUp() {
        UUID memberId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        UUID concertId = UUID.fromString("2e50b778-4de9-40e1-b9ba-9b1e786b4197");
        TokenCommand.CreateToken request = new TokenCommand.CreateToken(memberId, concertId);
        TOKEN_1 = new Token(memberId, concertId);

        UUID memberId2 = UUID.fromString("9687369c-3362-4d1b-bbba-667a8ef37ab8");
        UUID concertId2 = UUID.fromString("2e50b778-4de9-40e1-b9ba-9b1e786b4197");
        TokenCommand.CreateToken request2 = new TokenCommand.CreateToken(memberId2, concertId2);
        TOKEN_2 = new Token(memberId2, concertId2);

        // when
        tokenFacade.getToken(request);
        tokenFacade.getToken(request2);

    }

    @DisplayName("대기열 토큰 활성화 처리 정상 확인 ")
    @Test
    void activateToken() {
        // when
        tokenScheduler.activateToken();

        //then
        assertEquals(redisTemplate.opsForSet().isMember(ACTIVATE_KEY,TOKEN_1.toString()),true);
        assertEquals(redisTemplate.opsForSet().isMember(ACTIVATE_KEY,TOKEN_2.toString()),true);

    }


}