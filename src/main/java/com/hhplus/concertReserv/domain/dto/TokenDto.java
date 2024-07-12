package com.hhplus.concertReserv.domain.dto;

import com.hhplus.concertReserv.domain.entity.Token;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class TokenDto {
    Long tokenId;
    UUID memberId;
    UUID concertId;
    Long waitOrder;
    int status;
    LocalDateTime createDt;
    LocalDateTime activateDt;

    public TokenDto(Token token){
        this.tokenId = token.getTokenId();
        this.memberId = token.getMemberId();
        this.concertId = token.getConcertId();
        this.waitOrder = token.getWaitOrder();
        this.status = token.getStatus();
        this.createDt = token.getCreateDt();
        this.activateDt = token.getActivateDt();
    }
}
