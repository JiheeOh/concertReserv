package com.hhplus.concertReserv.domain.concert.repositories;

import com.hhplus.concertReserv.domain.concert.entity.Seat;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SeatRepository {
    List<Seat> findReserveAvailable(UUID concertId);

    Optional<Seat> findSeat(UUID seatId);

    void updateStatus(List<Seat> seat);
}