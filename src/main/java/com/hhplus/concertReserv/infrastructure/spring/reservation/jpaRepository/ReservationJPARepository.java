package com.hhplus.concertReserv.infrastructure.spring.reservation.jpaRepository;

import com.hhplus.concertReserv.domain.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReservationJPARepository extends JpaRepository<Reservation, UUID> {

}
