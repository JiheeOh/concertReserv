package com.hhplus.concertReserv.application;

import com.hhplus.concertReserv.domain.dto.PointDto;
import com.hhplus.concertReserv.domain.service.PointService;
import com.hhplus.concertReserv.domain.service.ValidationService;
import com.hhplus.concertReserv.exception.TokenNotFoundException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PointFacade {

    private final PointService pointService;
    private final ValidationService validationService;

    public PointFacade(PointService pointService,ValidationService validationService){
        this.pointService = pointService;
        this.validationService = validationService;
    }


    public PointDto charge(PointCommand.Charge requestBody) {
        return pointService.charge(requestBody.memberId(),requestBody.amount());
    }

    public PointDto paid(PointCommand.Paid requestBody) {
        if(validationService.isActivateToken(requestBody.tokenId())){
            throw new TokenNotFoundException("Token is deActivated",500);
        }
        return pointService.paid(requestBody.paymentId(),requestBody.amount());
    }

    public PointDto getPoint(UUID memberId) {
        return pointService.getPoint(memberId);
    }
}
