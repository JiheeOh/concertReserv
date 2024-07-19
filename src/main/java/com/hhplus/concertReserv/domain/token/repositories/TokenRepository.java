package com.hhplus.concertReserv.domain.token.repositories;

import com.hhplus.concertReserv.domain.token.dto.TokenDto;
import com.hhplus.concertReserv.domain.token.entity.Token;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TokenRepository {
    void save(Token token);

    Optional<Token> getLastOne(UUID concertId);

    void activateToken(int count);

    void deactivateToken(int dueTime);

    Optional<TokenDto> findActivateToken(Long tokenId);

    Optional<Token> isToken(Long tokenId);

    void deactivateNotPaidToken(List<Long> tokenIds);

    void deactivateToken(Long tokenId);

    int countActiveToken();
}
