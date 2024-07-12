package com.hhplus.concertReserv.domain.repository;

import com.hhplus.concertReserv.domain.entity.Reservation;

import java.util.List;


public interface ReservationRepository {
    Reservation save(Reservation reservation);

    void deleteExpired(List<Reservation> reservation);
}
