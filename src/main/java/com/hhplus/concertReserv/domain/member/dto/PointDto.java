package com.hhplus.concertReserv.domain.member.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class PointDto {
    boolean result;
    String message;
    Long point;
    UUID tokenId;

    public PointDto(){
        this.result=true;
    }

    public boolean getResult() {
        return this.result;
    }
}
