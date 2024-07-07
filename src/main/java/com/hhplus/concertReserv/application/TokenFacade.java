package com.hhplus.concertReserv.application;

import com.hhplus.concertReserv.domain.dto.TokenDto;
import com.hhplus.concertReserv.domain.service.TokenService;
import org.springframework.stereotype.Component;

@Component
public class TokenFacade {
    private final TokenService tokenService;
    public TokenFacade(TokenService tokenService){
        this.tokenService = tokenService;
    }

    public TokenDto getToken(TokenCommand.CreateToken authorization) {
        return tokenService.getToken(authorization.memberId(),authorization.concertId());
    }
}
