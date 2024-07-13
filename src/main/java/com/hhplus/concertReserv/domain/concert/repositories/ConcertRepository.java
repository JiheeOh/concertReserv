package com.hhplus.concertReserv.domain.concert.repositories;

import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

public interface ConcertRepository {
    Optional<Object> findConcert(UUID concertId);
}
