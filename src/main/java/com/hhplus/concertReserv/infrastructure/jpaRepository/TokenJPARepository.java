package com.hhplus.concertReserv.infrastructure.jpaRepository;

import com.hhplus.concertReserv.domain.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TokenJPARepository extends JpaRepository<Token, Long> {
}
