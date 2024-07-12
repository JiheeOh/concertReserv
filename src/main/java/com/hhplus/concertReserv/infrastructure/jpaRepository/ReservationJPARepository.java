package com.hhplus.concertReserv.infrastructure.jpaRepository;

import com.hhplus.concertReserv.domain.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReservationJPARepository extends JpaRepository<Reservation, UUID> {

}
