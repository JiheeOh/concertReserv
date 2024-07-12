package com.hhplus.concertReserv.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PointDto {
    boolean result;
    String message;
    Long point;

    public PointDto(){
        this.result=true;
    }

    public boolean getResult() {
        return this.result;
    }
}
