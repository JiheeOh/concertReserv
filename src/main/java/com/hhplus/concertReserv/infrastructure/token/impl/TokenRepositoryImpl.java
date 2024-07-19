package com.hhplus.concertReserv.infrastructure.token.impl;

import com.hhplus.concertReserv.domain.token.dto.TokenDto;
import com.hhplus.concertReserv.domain.token.entity.Token;
import com.hhplus.concertReserv.domain.token.repositories.TokenRepository;
import com.hhplus.concertReserv.infrastructure.token.jpaRepository.TokenJPARepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class TokenRepositoryImpl implements TokenRepository {
    private final TokenJPARepository tokenJPARepository;

    public TokenRepositoryImpl(TokenJPARepository tokenJPARepository){
        this.tokenJPARepository = tokenJPARepository;
    }
    @Override
    public void save(Token token) {
        tokenJPARepository.save(token);
    }

    @Override
    public Optional<Token> getLastOne(UUID concertId) {
        // 대기 중인 concertId별 대기열 중 제일 마지막 대기열 토큰 정보 가져오기
        return tokenJPARepository.getLastOne(concertId);
    }

    @Override
    public int countActiveToken() {
        // 활성화된 토큰( status = 0 )의 개수 구하기
        return tokenJPARepository.countActiveToken();
    }

    @Override
    public void activateToken(int count) {
        // memberId, concertId, create_dt orderBy 에서 count개수까지 status = 0 처리
        // 동시에 activate_at 에 sysdate 추가
        tokenJPARepository.updateTokenStatusActivate(count);
    }

    @Override
    public void deactivateToken(int dueTime) {
        // status = 0 이고 activate_at과 현재시간의 차이가 30분이상인 토큰들
        tokenJPARepository.updateTokenStatusDeactivate(dueTime);
    }

    @Override
    public Optional<TokenDto> findActivateToken(Long tokenId) {
        // 대기열 통과 후 활성화된 토큰인지 확인
        return tokenJPARepository.findActivateToken(tokenId);
    }

    @Override
    public Optional<Token> isToken(Long tokenId) {
        // 대기열에 등록되어있는 토큰인지 확인
        return tokenJPARepository.findById(tokenId);
    }

    @Override
    public void deactivateNotPaidToken(List<Long> tokenId) {
        //여러토큰 비활성화
        tokenJPARepository.updateTokenStatusDeactivate(tokenId);
    }

    @Override
    public void deactivateToken(Long tokenId) {
        // 특정 토큰 ID 비활성화
        tokenJPARepository.updateTokenStatusDeactivate(tokenId);
    }
}
