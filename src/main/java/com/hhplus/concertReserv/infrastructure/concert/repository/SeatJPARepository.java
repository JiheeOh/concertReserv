package com.hhplus.concertReserv.infrastructure.concert.repository;

import com.hhplus.concertReserv.domain.concert.entity.Seat;
import com.hhplus.concertReserv.domain.concert.entity.SeatPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SeatJPARepository extends JpaRepository<Seat, SeatPK> {
    @Query("SELECT s FROM Seat s WHERE s.concertSchedule.concert.concertId = :concertId")
    List<Seat> findByConcertId(@Param("concertId") UUID concertId);

}
