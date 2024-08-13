package com.hhplus.concertReserv.domain.token.service;

import com.hhplus.concertReserv.domain.token.dto.TokenDto;
import com.hhplus.concertReserv.domain.token.entity.Token;
import com.hhplus.concertReserv.domain.token.repositories.TokenRepository;
import com.hhplus.concertReserv.exception.TokenNotFoundException;
import com.hhplus.concertReserv.infrastructure.redis.token.impl.TokenRedisRepositoryImpl;
import com.hhplus.concertReserv.domain.common.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class TokenService {
    private final TokenRepository tokenRepository;

    public TokenService(TokenRedisRepositoryImpl tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

//    /**
//     * 대기열 등록
//     *
//     * @param memberId  사용자 아이디
//     * @param concertId 콘서트 아이디
//     * @return 등록된 토큰
//     */
//    public TokenDto createToken(UUID memberId, UUID concertId) {
//        log.info(" ==== createToken() start ====");
//        Token newOne = new Token();
//        try{
//            // 대기 중인 제일 마지막 데이터 가져오기
//            Token lastOne = tokenRepository.getLastOne(concertId).orElse(new Token());
//
//            // 마지막 사람 기준으로 대기 토큰 생성
//            newOne.setWaitOrder(lastOne.getWaitOrder() == null ? 0 : lastOne.getWaitOrder() + 1);
//            // 대기 상태  : 1
//            newOne.setStatus(1);
//            newOne.setMemberId(memberId);
//            newOne.setConcertId(concertId);
//
//            tokenRepository.save(newOne);
//
//            log.info(" ==== createToken() end ====");
//        }catch (Exception e){
//            log.error(e.toString());
//
//        }
//
//        return new TokenDto(newOne);
//
//    }

    /**
     * 대기열 토큰 생성
     *
     * @param memberId  사용자 ID
     * @param concertId 콘서트 ID
     * @return 대기열에 등록된 토큰
     */
    public TokenDto createToken(UUID memberId, UUID concertId) {
        log.info(" ==== createToken() start ====");
        Token newOne = new Token(memberId, concertId);
        try {
            tokenRepository.save(newOne);
            log.info(" ==== createToken() end ====");
        } catch (Exception e) {
            log.error(e.toString());

        }

        return new TokenDto(newOne);

    }


    /**
     * 내 토큰이 활성화된 토큰인지 확인
     *
     * @param memberId  사용자 ID
     * @param concertId 콘서트 ID
     * @return 활성화된 토큰의 정보, 비활성화 토큰일 경우 exception 처리
     */
    public boolean findActivateToken(UUID memberId, UUID concertId) {
        log.info(" ==== findActivateToken() start ====");
        boolean result = false;
        try {
            Token newOne = new Token(memberId, concertId);
            result = tokenRepository.findActivateToken(newOne);
            log.info(" ==== findActivateToken() end ====");
        }catch (Exception e){
            log.error(e.toString());
        }

        if (!result) {
            throw new TokenNotFoundException(ErrorCode.TOKEN_DEACTIVATED);
        }

        return result;
    }




    /**
     * 토큰 만료 처리
     * @param memberId 사용자 아이디
     * @param concertId 콘서트 아이디
     */
    public void deactivateToken(UUID memberId, UUID concertId) {
        log.info("=== deactivateToken() started ===");
        try {
            Token token = new Token(memberId, concertId);
            tokenRepository.deactivateToken(token);
            log.info("=== deactivateToken() end ===");
        }catch (Exception e){
            log.error(e.toString());
        }
    }
}
