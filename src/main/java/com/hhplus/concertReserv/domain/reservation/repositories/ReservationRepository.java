package com.hhplus.concertReserv.domain.reservation.repositories;

import com.hhplus.concertReserv.domain.reservation.entity.Reservation;

import java.util.List;


public interface ReservationRepository {
    Reservation save(Reservation reservation);

    void deleteExpired(List<Reservation> reservation);
}
