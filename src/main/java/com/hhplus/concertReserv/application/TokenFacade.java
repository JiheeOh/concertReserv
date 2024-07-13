package com.hhplus.concertReserv.application;

import com.hhplus.concertReserv.domain.token.dto.TokenDto;
import com.hhplus.concertReserv.domain.common.service.ValidationService;
import com.hhplus.concertReserv.domain.token.service.TokenService;
import com.hhplus.concertReserv.exception.TokenNotFoundException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TokenFacade {
    private final ValidationService validationService;
    private final TokenService tokenService;
    public TokenFacade(ValidationService validationService, TokenService tokenService){
        this.validationService = validationService;
        this.tokenService = tokenService;
    }

    public TokenDto getToken(TokenCommand.CreateToken authorization) {
        UUID memberId = authorization.memberId();
        UUID concertId = authorization.concertId();

        if(!validationService.checkMemberConcertValidate(memberId,concertId)){
            throw new IllegalArgumentException();
        }
        return tokenService.createToken(memberId,concertId);
    }

    public TokenDto findActivateToken(Long tokenId) {
        if(!tokenService.isToken(tokenId)){
            throw new TokenNotFoundException("There is no token",500);
        }
        return tokenService.findActivateToken(tokenId);
    }
}
