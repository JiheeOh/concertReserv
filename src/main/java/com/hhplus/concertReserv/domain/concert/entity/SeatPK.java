package com.hhplus.concertReserv.domain.concert.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Getter
@Setter
public class SeatPK implements Serializable {

    private UUID seatId;
    private String status;

}
