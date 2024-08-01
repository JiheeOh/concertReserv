package com.hhplus.concertReserv.interfaces.scheduler;

import com.hhplus.concertReserv.domain.token.repositories.TokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TokenScheduler {

    private final TokenRepository tokenRepository;

    public TokenScheduler(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    /**
     * 스케줄러로 대기열 처리 (1000명 유지하도록)
     * 1분마다 작동
     * 1. 1000명 통과처리 (놀이공원 방식)
     */
    @Scheduled(cron = "0 0/1 * * * *")
    public void activateToken() {
        // 유량 설정
        Long updateCount = 1000L;
        // 분 단위 설정 : 5분 뒤 만료
        int timeOut = 5;

        log.info(String.format("========== Update waiting status : %d ==========", updateCount));
        tokenRepository.activateToken(updateCount,timeOut);
        log.info(String.format("========== Update waiting status completed : %d ==========", updateCount));

    }
}
