package com.hhplus.concertReserv.domain.concert.infrastructure.impl;

import com.hhplus.concertReserv.domain.concert.entity.Seat;
import com.hhplus.concertReserv.domain.concert.entity.SeatPK;
import com.hhplus.concertReserv.domain.concert.repositories.SeatRepository;
import com.hhplus.concertReserv.domain.concert.infrastructure.repository.SeatJPARepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class SeatRepositoryImpl implements SeatRepository{
    private final SeatJPARepository seatJPARepository;

    public SeatRepositoryImpl(SeatJPARepository seatJPARepository){
        this.seatJPARepository = seatJPARepository;
    }
    @Override
    public List<Seat> findReserveAvailable(UUID concertId) {
        return seatJPARepository.findByConcertId(concertId);
    }

    @Override
    public Optional<Seat> findSeat(UUID seatId) {
        //TODO : seatId
        return seatJPARepository.findById(new SeatPK(seatId,null));
    }

    @Override
    public void updateStatus(List<Seat> seat) {
         seatJPARepository.saveAll(seat);
    }

}
