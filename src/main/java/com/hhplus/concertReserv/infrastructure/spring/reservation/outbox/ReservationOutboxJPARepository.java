package com.hhplus.concertReserv.infrastructure.spring.reservation.outbox;

import com.hhplus.concertReserv.domain.reservation.message.entity.ReservationOutbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReservationOutboxJPARepository extends JpaRepository<ReservationOutbox, UUID> {
    @Query("select r from ReservationOutbox r where r.seatId= :seatId and r.userId= :userId")
    Optional<ReservationOutbox> findBySeatIdUserId(UUID seatId, UUID userId);

    @Query(value = "select * from Reservation_Outbox r where r.status = 'INIT' and r.event_create_Dt > NOW() - INTERVAL 2 MINUTE and r.retry < 10",nativeQuery = true)
    Optional<List<ReservationOutbox>> findPublishFailed();
}
