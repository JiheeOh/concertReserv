package com.hhplus.concertReserv.application;

import com.hhplus.concertReserv.domain.token.dto.TokenDto;
import com.hhplus.concertReserv.domain.common.service.ValidationService;
import com.hhplus.concertReserv.domain.token.service.TokenService;
import com.hhplus.concertReserv.exception.TokenNotFoundException;
import com.hhplus.concertReserv.interfaces.presentation.ErrorCode;
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

    public TokenDto findActivateToken(UUID tokenId) {
        if(!tokenService.isToken(tokenId)){
            throw new TokenNotFoundException(ErrorCode.TOKEN_NOT_FOUND);
        }
        return tokenService.findActivateToken(tokenId);
    }
}
