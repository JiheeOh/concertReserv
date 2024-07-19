package com.hhplus.concertReserv.application;

import java.time.LocalDateTime;
import java.util.UUID;

public class ReserveCommand {
    public record ApplySeat(
            UUID memberId,
            UUID concertId,
            Long tokenId,
            LocalDateTime concertDate,
            UUID seatId
    ) {
        public ApplySeat(UUID memberId, UUID seatId) {
            this(memberId, null, null, null, seatId);
        }
    }


}
