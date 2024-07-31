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
    UUID tokenId;
    UUID memberId;
    UUID concertId;
    Long waitOrder;
    int status;
    double createDt;
    LocalDateTime activateDt;

    public TokenDto(Token token){
        this.tokenId = token.getTokenId();
        this.memberId = token.getMemberId();
        this.concertId = token.getConcertId();
        this.createDt = token.getCreateDt();
    }
}
