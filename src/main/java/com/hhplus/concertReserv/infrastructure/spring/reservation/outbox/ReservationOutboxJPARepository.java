package com.hhplus.concertReserv.infrastructure.spring.reservation.outbox;

import com.hhplus.concertReserv.domain.reservation.kafka.outbox.entity.ReservationOutbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReservationOutboxJPARepository extends JpaRepository<ReservationOutbox, UUID> {
    @Query("select r from ReservationOutbox r where r.seatId= :seatId and r.userId= :userId")
    Optional<ReservationOutbox> findBySeatIdUserId(UUID seatId, UUID userId);

    @Query(value = "select r from ReservationOutbox r where r.status = 'INIT' and r.eventCreateDt > NOW() - INTERVAL 10 MINUTE ",nativeQuery = true)
    Optional<List<ReservationOutbox>> findPublishFailed();
}
