package com.hhplus.concertReserv.domain.concert.entity;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;



@EqualsAndHashCode
public class ConcertSchedulePK implements Serializable {

    private LocalDateTime concertDt;
    private UUID concertId;

}
