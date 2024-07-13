package com.hhplus.concertReserv.application;

import java.time.LocalDateTime;
import java.util.UUID;

public class PointCommand {
    // TODO : Command에 대한 조사를 하자
    /**
     * 충전용 인자
     * @param memberId
     * @param amount
     */
    public record Charge(
            UUID memberId,
            Long amount
    ){
    }

    /**
     * 결제 처리용 인자
     */
    public record Paid(
            Long tokenId,
            UUID paymentId,
            LocalDateTime dueTime,
            Long amount
    ){
    }
}
