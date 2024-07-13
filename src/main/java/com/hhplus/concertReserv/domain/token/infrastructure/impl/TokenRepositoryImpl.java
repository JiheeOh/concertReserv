package com.hhplus.concertReserv.domain.token.infrastructure.impl;

import com.hhplus.concertReserv.domain.token.dto.TokenDto;
import com.hhplus.concertReserv.domain.token.entity.Token;
import com.hhplus.concertReserv.domain.token.repositories.TokenRepository;
import com.hhplus.concertReserv.domain.token.infrastructure.jpaRepository.TokenJPARepository;
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
        // TODO: 세부 구현 필요
        // memberId, concertId, create_dt orderBy && status = 1
        return null;
    }

    @Override
    public int count() {
        // TODO : 세부 구현 필요
        // select count(1) from Token where  status = 0
        return 0;
    }

    @Override
    public void activateToken(int count) {
        // TODO : 세부 구현 필요
        // memberId, concertId, create_dt orderBy 에서 200명까지 status = 0 처리
        // 동시에 activate_at 에 sysdate 추가
    }

    @Override
    public void deactivateToken() {
        // TODO : 활성화 상태가 30분이상인 토큰 만료
        // status = 0 이고 activate_at과 현재시간의 차이가 30분이상인 토큰들
    }

    @Override
    public Optional<TokenDto> findActivateToken(Long tokenId) {
        // TODO : 대기열에 등록된 토큰이 활성화 상태일 경우 토큰 반환, 활성화가 아닌 경우 null 반환
        return null;
    }

    @Override
    public Optional<TokenDto> isToken(Long tokenId) {
        // TODO : 대기열에 등록된 토큰일 경우 토큰 반환,아닌 경우 null 반환
        return null;
    }

    @Override
    public void deactivateNotPaidToken(List<Long> tokenId) {
        // TODO : tokenId에 해당되는 토큰들 status 0 전환
    }

    @Override
    public void deactivateToken(Long tokenId) {
        // TODO : 특정 token status 0 전환
    }
}
