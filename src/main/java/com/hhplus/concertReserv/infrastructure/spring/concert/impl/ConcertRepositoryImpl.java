package com.hhplus.concertReserv.infrastructure.spring.concert.impl;

import com.hhplus.concertReserv.domain.concert.entity.Concert;
import com.hhplus.concertReserv.domain.concert.repositories.ConcertRepository;
import com.hhplus.concertReserv.infrastructure.spring.concert.repository.ConcertJPARepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class ConcertRepositoryImpl implements ConcertRepository {
    private final ConcertJPARepository concertJPARepository;

    public ConcertRepositoryImpl(ConcertJPARepository concertJPARepository){
        this.concertJPARepository = concertJPARepository;
    }
    @Override
    public Optional<Concert> findConcert(UUID concertId) {
        return concertJPARepository.findById(concertId);
    }

}
