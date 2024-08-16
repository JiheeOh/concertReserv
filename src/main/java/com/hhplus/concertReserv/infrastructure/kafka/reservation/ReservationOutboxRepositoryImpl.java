package com.hhplus.concertReserv.infrastructure.kafka.reservation;

import com.hhplus.concertReserv.domain.reservation.kafka.outbox.ReservationOutboxRepository;
import com.hhplus.concertReserv.domain.reservation.kafka.outbox.entity.ReservationOutbox;
import com.hhplus.concertReserv.infrastructure.spring.reservation.outbox.ReservationOutboxJPARepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ReservationOutboxRepositoryImpl implements ReservationOutboxRepository {

    private final ReservationOutboxJPARepository outboxJPARepository;

    public ReservationOutboxRepositoryImpl(ReservationOutboxJPARepository outboxJPARepository) {
        this.outboxJPARepository = outboxJPARepository;
    }

    @Override
    public void save(ReservationOutbox outbox) {
        outboxJPARepository.save(outbox);
    }

    @Override
    public void complete(ReservationOutbox outbox) {
        outboxJPARepository.save(outbox);
    }

    @Override
    public Optional<ReservationOutbox> findBySeatIdUserId(UUID seatId, UUID userId) {
        return outboxJPARepository.findBySeatIdUserId(seatId,userId);
    }

    @Override
    public Optional<List<ReservationOutbox>> findPublishFailed() {
        return outboxJPARepository.findPublishFailed();
    }




}
