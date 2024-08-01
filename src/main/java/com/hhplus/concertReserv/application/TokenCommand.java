package com.hhplus.concertReserv.application;

import java.util.UUID;

public class TokenCommand {

    /**
     * 토큰 생성용 인자
     */
    public record CreateToken(
            UUID memberId,
            UUID concertId
    )
    {
        public CreateToken(UUID memberId, UUID concertId) {
            this.memberId = memberId;
            this.concertId = concertId;
        }
    }
}
