package com.hhplus.concertReserv.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenDto {
    String token;

    public TokenDto(String token){
        this.token = token;
    }
}
