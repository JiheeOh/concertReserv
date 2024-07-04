package com.hhplus.concertReserv.application;

import com.hhplus.concertReserv.domain.dto.PointDto;
import com.hhplus.concertReserv.domain.service.PointService;
import org.springframework.stereotype.Component;

@Component
public class PointFacade {

    private final PointService pointService;

    public PointFacade(PointService pointService){
        this.pointService = pointService;
    }


    public PointDto charge(String authorization, PointCommand.Charge requestBody) {
        return pointService.charge(requestBody.memberId(),requestBody.amount());
    }

    public PointDto paid(String authorization, PointCommand.Paid requestBody) {
        return pointService.paid(requestBody.payId(),requestBody.dueTime(),requestBody.amount());
    }
}
