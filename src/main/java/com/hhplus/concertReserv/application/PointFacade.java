package com.hhplus.concertReserv.application;

import com.hhplus.concertReserv.domain.member.dto.PointDto;
import com.hhplus.concertReserv.domain.member.service.PointService;
import com.hhplus.concertReserv.domain.token.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
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
        PointDto result = new PointDto();

        boolean paidSuccess = false;
        int retryCount = 0;

        while (!paidSuccess && retryCount < 10000) {
            retryCount += 1;
            try {
                result = pointService.paid(requestBody.paymentId(), requestBody.amount());
                paidSuccess = true;
                // 토큰 만료화
                if (result.getTokenId() != null) {
                    tokenService.deactivateToken(result.getTokenId());
                }
            } catch (Exception e) {
                log.error(e.toString());
                log.info(String.format("======= retryCount : %d, amount : %d ======", retryCount,requestBody.amount()));

            }
        }
        return result;
    }

    public PointDto getPoint(UUID memberId) {
        return pointService.getPoint(memberId);
    }
}
