package com.hhplus.concertReserv.domain.reservation.kafka.outbox;

import com.hhplus.concertReserv.domain.reservation.kafka.outbox.entity.ReservationOutbox;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReservationOutboxRepository {
    void save(ReservationOutbox outbox);

    void complete(ReservationOutbox outbox);

    Optional<ReservationOutbox> findBySeatIdUserId(UUID seatId, UUID userId);

    Optional<List<ReservationOutbox>> findPublishFailed();

}
