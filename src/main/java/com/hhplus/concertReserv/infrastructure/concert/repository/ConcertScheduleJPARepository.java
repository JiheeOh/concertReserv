package com.hhplus.concertReserv.infrastructure.concert.repository;

import com.hhplus.concertReserv.domain.concert.entity.ConcertSchedule;
import com.hhplus.concertReserv.domain.concert.entity.ConcertSchedulePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConcertScheduleJPARepository extends JpaRepository<ConcertSchedule, ConcertSchedulePK> {

    @Query("SELECT s FROM ConcertSchedule s WHERE s.delYn= 'N'")
    List<ConcertSchedule> findAllByDelYn();
}
