package com.hhplus.concertReserv.domain.concert.infrastructure.repository;

import com.hhplus.concertReserv.domain.concert.entity.ConcertSchedule;
import com.hhplus.concertReserv.domain.concert.entity.ConcertSchedulePK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertScheduleJPARepository extends JpaRepository<ConcertSchedule, ConcertSchedulePK> {
}
