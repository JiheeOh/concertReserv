package com.hhplus.concertReserv.domain.repository;

import com.hhplus.concertReserv.domain.dto.TokenDto;
import com.hhplus.concertReserv.domain.entity.Token;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TokenRepository {
    void save(Token token);

    Optional<Token> getLastOne(UUID concertId);

    int count();

    void activateToken(int count);

    void deactivateToken();

    Optional<TokenDto> findActivateToken(Long tokenId);

    Optional<TokenDto> isToken(Long tokenId);

    void deactivateNotPaidToken(List<Long> tokenIds);

    void deactivateToken(Long tokenId);
}
