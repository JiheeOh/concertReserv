package com.hhplus.concertReserv.domain.token.dto;

import com.hhplus.concertReserv.domain.token.entity.Token;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
public class TokenDto {
    UUID memberId;
    UUID concertId;
    double createDt;

    public TokenDto(Token token){
        this.memberId = token.getMemberId();
        this.concertId = token.getConcertId();
        this.createDt = token.getCreateDt();
    }


}
