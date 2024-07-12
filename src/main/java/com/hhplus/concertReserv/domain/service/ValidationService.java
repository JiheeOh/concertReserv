package com.hhplus.concertReserv.domain.service;

import com.hhplus.concertReserv.domain.repository.ConcertRepository;
import com.hhplus.concertReserv.domain.repository.MemberRepository;
import com.hhplus.concertReserv.domain.repository.TokenRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ValidationService {
    private final MemberRepository memberRepository;
    private final ConcertRepository concertRepository;

    private final TokenRepository tokenRepository;

    public ValidationService(MemberRepository memberRepository
            , ConcertRepository concertRepository
            , TokenRepository tokenRepository) {
        this.memberRepository = memberRepository;
        this.concertRepository = concertRepository;
        this.tokenRepository = tokenRepository;
    }

    /**
     * 대기열 토큰 발급 전 유효성 판단
     * 1. 등록된 사용자인지
     * 2. 등록된 콘서트인지
     *
     * @param memberId  사용자 ID
     * @param concertId 콘서트 ID
     * @return 모든 조건이 유효할 경우 true 반환, 하나라도 아닐 시 false 반환
     */
    public boolean checkMemberConcertValidate(UUID memberId, UUID concertId) {

        // 유효한 사용자인지 확인
        if (!isMember(memberId)) {
            return false;
        }
        // 유효한 콘서트인지 확인
        if (!isConcert(concertId)) {
            return false;
        }

        return true;
    }

    private boolean isMember(UUID memberId) {
        return memberRepository.findMember(memberId).isPresent();
    }

    private boolean isConcert(UUID concertId) {
        return concertRepository.findConcert(concertId).isPresent();
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
