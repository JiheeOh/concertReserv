package com.hhplus.concertReserv.domain.service;

import com.hhplus.concertReserv.domain.dto.TokenDto;
import com.hhplus.concertReserv.domain.entity.Token;
import com.hhplus.concertReserv.domain.repository.TokenRepository;
import com.hhplus.concertReserv.exception.TokenNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class TokenService {
    private final TokenRepository tokenRepository;

    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    /**
     * 대기열 등록
     *
     * @param memberId  사용자 아이디
     * @param concertId 콘서트 아이디
     * @return 등록된 토큰
     */
    public TokenDto createToken(UUID memberId, UUID concertId) {

        // 대기 중인 제일 마지막 데이터 가져오기
        Token lastOne = tokenRepository.getLastOne(concertId).orElse(new Token());


        // 마지막 사람 기준으로 대기 토큰 생성
        Token newOne = new Token();
        newOne.setWaitOrder(lastOne.getWaitOrder() == null ? 0 : lastOne.getWaitOrder() + 1);
        // TODO : ENUM 처리할 것
        newOne.setStatus(1);
        newOne.setMemberId(memberId);
        newOne.setConcertId(concertId);

        tokenRepository.save(newOne);

        return new TokenDto(newOne);

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

        log.info(String.format("========== Update waiting status : %d ==========", updateCount));
        int activatedTokenCount = tokenRepository.count();
        if (activatedTokenCount < updateCount) {
            tokenRepository.activateToken(updateCount);
        }else{
            tokenRepository.deactivateToken();
        }
        log.info(String.format("========== Update waiting status completed : %d ==========", updateCount));

    }

    /**
     * 내 토큰이 활성화된 토큰인지 확인
     * @param tokenId 토큰 ID
     * @return 활성화된 토큰의 정보, 비활성화 토큰일 경우 exception 처리
     */
    public TokenDto findActivateToken(Long tokenId) {
        return tokenRepository.findActivateToken(tokenId).orElseThrow(()->new TokenNotFoundException("Token is deactivated",500));
    }
}
