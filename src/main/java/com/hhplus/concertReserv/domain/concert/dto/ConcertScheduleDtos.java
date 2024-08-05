package com.hhplus.concertReserv.domain.concert.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ConcertScheduleDtos implements Serializable {
    private List<ConcertScheduleDto> dtos = new ArrayList<>();

    public ConcertScheduleDtos(List<ConcertScheduleDto> dtos){
        this.dtos = dtos;
    }
}
