package com.hhplus.concertReserv.application;

import java.time.LocalDateTime;
import java.util.UUID;

public class PointCommand {
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
            UUID tokenId,
            UUID paymentId,
            LocalDateTime dueTime,
            Long amount
    ){
        public Paid(UUID tokenId,UUID paymentId, Long amount) {
            this(tokenId, paymentId, null, amount);
        }
    }
}
