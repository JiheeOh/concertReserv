package com.hhplus.concertReserv.infrastructure.concert.repository;

import com.hhplus.concertReserv.domain.concert.entity.Concert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ConcertJPARepository extends JpaRepository<Concert, UUID> {
}
