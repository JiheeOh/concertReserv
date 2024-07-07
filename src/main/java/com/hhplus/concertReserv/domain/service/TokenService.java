package com.hhplus.concertReserv.domain.service;

import com.hhplus.concertReserv.application.TokenCommand;
import com.hhplus.concertReserv.domain.dto.TokenDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TokenService {
    public TokenDto getToken(UUID memberId, UUID concertId) {
        return new TokenDto("hello");
    }
}
