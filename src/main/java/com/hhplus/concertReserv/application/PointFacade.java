package com.hhplus.concertReserv.application;

import com.hhplus.concertReserv.domain.member.dto.PointDto;
import com.hhplus.concertReserv.domain.member.service.PointService;
import com.hhplus.concertReserv.domain.token.service.TokenService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PointFacade {

    private final PointService pointService;
    private final TokenService tokenService;


    public PointFacade(PointService pointService,TokenService tokenService){
        this.pointService = pointService;
        this.tokenService = tokenService;
    }


    public PointDto charge(PointCommand.Charge requestBody) {
        return pointService.charge(requestBody.memberId(),requestBody.amount());
    }

    public PointDto paid(PointCommand.Paid requestBody) {
        PointDto result = pointService.paid(requestBody.paymentId(),requestBody.amount());
        if(result.getTokenId() != null){
            tokenService.deactivateToken(result.getTokenId());
        }
        return result;
    }

    public PointDto getPoint(UUID memberId) {
        return pointService.getPoint(memberId);
    }
}
