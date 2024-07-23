package com.hhplus.concertReserv.domain.concert.repositories;

import com.hhplus.concertReserv.domain.concert.entity.Concert;

import java.util.Optional;
import java.util.UUID;

public interface ConcertRepository {
    Optional<Concert> findConcert(UUID concertId);
}
