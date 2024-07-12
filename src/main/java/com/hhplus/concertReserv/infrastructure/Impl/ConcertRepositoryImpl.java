package com.hhplus.concertReserv.infrastructure.Impl;

import com.hhplus.concertReserv.domain.repository.ConcertRepository;
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
