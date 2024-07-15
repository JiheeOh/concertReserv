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
    }


}
