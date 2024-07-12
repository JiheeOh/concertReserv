package com.hhplus.concertReserv.infrastructure.jpaRepository;

import com.hhplus.concertReserv.domain.entity.Seat;
import com.hhplus.concertReserv.domain.entity.SeatPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface SeatJPARepository extends JpaRepository<Seat, SeatPK> {
    @Query("SELECT s FROM Seat s WHERE s.concertSchedule.concertId.concertId = :concertId")
    List<Seat> findByConcertId(@Param("concertId") UUID concertId);

}
