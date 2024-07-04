package com.hhplus.concertReserv.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PointDto {
    String result;
    String message;
    Long point;

    public PointDto(){
        this.result="success";
    }
}
