package com.hhplus.concertReserv.infrastructure.kafka.reservation;

import com.hhplus.concertReserv.domain.reservation.message.ReservationOutboxRepository;
import com.hhplus.concertReserv.domain.reservation.message.entity.ReservationOutbox;
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

    /**
     * 이벤트 생성시간이 10분이상이면서, retry 횟수가 10보다 적은 이벤트 가져오기
     */
    @Override
    public Optional<List<ReservationOutbox>> findPublishFailed() {
        return outboxJPARepository.findPublishFailed();
    }




}
