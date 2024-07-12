package com.hhplus.concertReserv.infrastructure.jpaRepository;

import com.hhplus.concertReserv.domain.entity.ConcertSchedule;
import com.hhplus.concertReserv.domain.entity.ConcertSchedulePK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertScheduleJPARepository extends JpaRepository<ConcertSchedule, ConcertSchedulePK> {
}
