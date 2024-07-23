package com.hhplus.concertReserv.application;

import com.hhplus.concertReserv.domain.member.dto.PointDto;
import com.hhplus.concertReserv.domain.member.service.PointService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PointFacade {

    private final PointService pointService;


    public PointFacade(PointService pointService){
        this.pointService = pointService;
    }


    public PointDto charge(PointCommand.Charge requestBody) {
        return pointService.charge(requestBody.memberId(),requestBody.amount());
    }

    public PointDto paid(PointCommand.Paid requestBody) {
        return pointService.paid(requestBody.paymentId(),requestBody.amount());
    }

    public PointDto getPoint(UUID memberId) {
        return pointService.getPoint(memberId);
    }
}
