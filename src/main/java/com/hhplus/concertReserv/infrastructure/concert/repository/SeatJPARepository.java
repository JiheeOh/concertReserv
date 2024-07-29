package com.hhplus.concertReserv.infrastructure.concert.repository;

import com.hhplus.concertReserv.domain.concert.entity.Seat;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SeatJPARepository extends JpaRepository<Seat, UUID> {
    @Query("SELECT s FROM Seat s WHERE s.concertSchedule.concertId.concertId = :concertId")
    List<Seat> findByConcertId(@Param("concertId") UUID concertId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM Seat s WHERE s.seatId = :seatId and s.status = 'AVAILABLE'")
    Optional<Seat> findByIdWithLock(UUID seatId);
}
