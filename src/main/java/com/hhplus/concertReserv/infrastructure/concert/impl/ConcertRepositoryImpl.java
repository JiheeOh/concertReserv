package com.hhplus.concertReserv.infrastructure.concert.impl;

import com.hhplus.concertReserv.domain.concert.repositories.ConcertRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class ConcertRepositoryImpl implements ConcertRepository {
    @Override
    public Optional<Object> findConcert(UUID concertId) {
        return Optional.empty();
    }
}
