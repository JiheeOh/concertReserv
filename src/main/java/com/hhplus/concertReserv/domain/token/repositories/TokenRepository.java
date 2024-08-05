package com.hhplus.concertReserv.domain.token.repositories;

import com.hhplus.concertReserv.domain.token.dto.TokenDto;
import com.hhplus.concertReserv.domain.token.entity.Token;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TokenRepository {
    void save(Token token);

    void activateToken(Long count, int timeout);

    boolean findActivateToken(Token token);


    void deactivateToken(Token tokenId);

}
