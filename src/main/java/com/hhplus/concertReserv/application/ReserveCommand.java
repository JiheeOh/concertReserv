package com.hhplus.concertReserv.application;

import java.time.LocalDateTime;
import java.util.UUID;

public class ReserveCommand {
    public record ApplySeat(
            UUID memberId,
            UUID concertId,
            UUID tokenId,
            LocalDateTime concertDate,
            UUID seatId
    ) {
        public ApplySeat(UUID memberId, UUID seatId,UUID tokenId) {
            this(memberId, null, tokenId, null, seatId);
        }
    }


}
