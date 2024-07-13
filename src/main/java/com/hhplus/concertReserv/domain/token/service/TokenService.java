package com.hhplus.concertReserv.domain.token.service;

import com.hhplus.concertReserv.domain.token.dto.TokenDto;
import com.hhplus.concertReserv.domain.token.entity.Token;
import com.hhplus.concertReserv.domain.token.repositories.TokenRepository;
import com.hhplus.concertReserv.exception.TokenNotFoundException;
import lombok.extern.slf4j.Slf4j;
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
     * 내 토큰이 활성화된 토큰인지 확인
     * @param tokenId 토큰 ID
     * @return 활성화된 토큰의 정보, 비활성화 토큰일 경우 exception 처리
     */
    public TokenDto findActivateToken(Long tokenId) {
        return tokenRepository.findActivateToken(tokenId).orElseThrow(()->new TokenNotFoundException("Token is deactivated",500));
    }

    /**
     * 활성화된 토큰인지 확인
     * @param tokenId 확인하려는 토큰 id
     * @return 활성화된 토큰이면 true 반환, 활성화된 토큰이 아니면 false 반환
     */
    public boolean isActivateToken(Long tokenId){
        if (tokenRepository.findActivateToken(tokenId).isEmpty()){
            return false;
        }
        return true;
    }

    /**
     * 대기열에 등록된 토큰인지 확인
     * @param tokenId 조회하려는 토큰
     * @return 등록되어있는 토큰이면 true, 아니면 false
     */
    public boolean isToken(Long tokenId){
        if(tokenRepository.isToken(tokenId).isEmpty()){
            return false;
        }
        return true;
    }
}
