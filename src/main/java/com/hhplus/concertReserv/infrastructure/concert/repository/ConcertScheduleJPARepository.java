package com.hhplus.concertReserv.infrastructure.concert.repository;

import com.hhplus.concertReserv.domain.concert.entity.ConcertSchedule;
import com.hhplus.concertReserv.domain.concert.entity.ConcertSchedulePK;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ConcertScheduleJPARepository extends JpaRepository<ConcertSchedule, ConcertSchedulePK> {

    @Query("SELECT s FROM ConcertSchedule s WHERE s.delYn= 'N'")
    List<ConcertSchedule> findAllByDelYn();

    @Query("SELECT s FROM ConcertSchedule s WHERE s.concertId.concertId = :concertId and s.delYn = 'N'")
    List<ConcertSchedule> findByConcertId(UUID concertId);

    @Transactional
    @Modifying
    @Query("UPDATE ConcertSchedule s SET s.delYn = 'Y' where s.concertDt= :concertDt and s.concertId.concertId = :concertId ")
    void updateDelYnToN(UUID concertId, LocalDateTime concertDt);
}
