package com.hhplus.concertReserv.infrastructure.Impl;


import com.hhplus.concertReserv.domain.entity.Reservation;
import com.hhplus.concertReserv.domain.entity.Seat;
import com.hhplus.concertReserv.domain.repository.ReservationRepository;
import com.hhplus.concertReserv.infrastructure.jpaRepository.ReservationJPARepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

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
