package com.hhplus.concertReserv.application;

import com.hhplus.concertReserv.domain.token.dto.TokenDto;
import com.hhplus.concertReserv.domain.common.service.ValidationService;
import com.hhplus.concertReserv.domain.token.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
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
            log.error(" ==== Not validate memberId or concertId ==== ");
            throw new IllegalArgumentException();
        }
        return tokenService.createToken(memberId,concertId);
    }

    public boolean findActivateToken(TokenCommand.CreateToken authorization) {
        UUID memberId = authorization.memberId();
        UUID concertId = authorization.concertId();

        return tokenService.findActivateToken(memberId,concertId);
    }
}
