package com.hhplus.concertReserv.domain.repository;

import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

public interface ConcertRepository {
    Optional<Object> findConcert(UUID concertId);
}
