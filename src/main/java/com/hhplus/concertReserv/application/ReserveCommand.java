package com.hhplus.concertReserv.application;

import java.time.LocalDateTime;
import java.util.UUID;

public class ReserveCommand {
    public record ApplySeat(
            UUID concertId,
            LocalDateTime concertDate,
            UUID seatId
    ) {
    }


}
