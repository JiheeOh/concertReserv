package com.hhplus.concertReserv.infrastructure.jpaRepository;

import com.hhplus.concertReserv.domain.entity.Concert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ConcertJPARepository extends JpaRepository<Concert, UUID> {
}
