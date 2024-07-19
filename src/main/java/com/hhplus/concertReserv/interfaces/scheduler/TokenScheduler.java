package com.hhplus.concertReserv.interfaces.scheduler;

import com.hhplus.concertReserv.domain.token.repositories.TokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
public class TokenScheduler {

    private final TokenRepository tokenRepository;

    public TokenScheduler(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    /**
     *
     * 스케줄러로 대기열 처리 (200명 유지하도록)
     * 1분마다 작동
     * 1. 200명 통과처리 (놀이공원 방식)
     * 2. 활성화인 토큰이 200이 넘을 경우, 활성화 상태가 30분째인 토큰 만료
     */
    @Scheduled(cron = "0 0/1 * * * *")
    private void waitStatusUpdate() {
        // 유량 설정
        int updateCount = 200;
        int dueTime = 30;

        log.info(String.format("========== Update waiting status : %d ==========", updateCount));
        int activatedTokenCount = tokenRepository.countActiveToken();
        if (activatedTokenCount < updateCount) {
            tokenRepository.activateToken(updateCount);
        } else {
            tokenRepository.deactivateToken(dueTime);
        }
        log.info(String.format("========== Update waiting status completed : %d ==========", updateCount));

    }
}
