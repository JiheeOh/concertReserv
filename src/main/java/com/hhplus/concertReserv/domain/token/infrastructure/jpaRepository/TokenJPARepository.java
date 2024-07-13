package com.hhplus.concertReserv.domain.token.infrastructure.jpaRepository;

import com.hhplus.concertReserv.domain.token.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TokenJPARepository extends JpaRepository<Token, Long> {
}
