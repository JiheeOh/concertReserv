package com.hhplus.concertReserv.domain.reservation.infrastructure.impl;


import com.hhplus.concertReserv.domain.reservation.entity.Reservation;
import com.hhplus.concertReserv.domain.reservation.repositories.ReservationRepository;
import com.hhplus.concertReserv.domain.reservation.infrastructure.jpaRepository.ReservationJPARepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReservationRepositoryImpl implements ReservationRepository {
    private final ReservationJPARepository reservationJPARepository;
    public ReservationRepositoryImpl(ReservationJPARepository reservationJPARepository){
        this.reservationJPARepository = reservationJPARepository;
    }
    @Override
    public Reservation save(Reservation reservation) {
        return reservationJPARepository.save(reservation);
    }

    @Override
    public void deleteExpired(List<Reservation> reservation) {
        reservationJPARepository.deleteAll(reservation);
    }
}
