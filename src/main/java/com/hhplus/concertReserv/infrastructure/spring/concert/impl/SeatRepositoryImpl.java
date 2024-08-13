package com.hhplus.concertReserv.infrastructure.spring.concert.impl;

import com.hhplus.concertReserv.domain.concert.entity.Seat;
import com.hhplus.concertReserv.domain.concert.repositories.SeatRepository;
import com.hhplus.concertReserv.infrastructure.spring.concert.repository.SeatJPARepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class SeatRepositoryImpl implements SeatRepository {
    private final SeatJPARepository seatJPARepository;

    public SeatRepositoryImpl(SeatJPARepository seatJPARepository) {
        this.seatJPARepository = seatJPARepository;
    }
    @Override
    public List<Seat> findReserveAvailable(UUID concertId, LocalDateTime concertDt) {
        return seatJPARepository.findByConcertId(concertId, concertDt);
    }

    @Override
    public Optional<Seat> findSeat(UUID seatId) {
        return seatJPARepository.findByIdWithLock(seatId);
    }


    @Override
    public void save(Seat seat) {
        seatJPARepository.save(seat);
    }
}
