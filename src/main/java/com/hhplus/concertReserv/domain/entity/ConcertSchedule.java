package com.hhplus.concertReserv.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@IdClass(ConcertSchedulePK.class)
public class ConcertSchedule extends BaseEntity {

    @Id
    @Column(name = "CONCERT_DT")
    private LocalDateTime concertDt;

    @Id
    @ManyToOne
    @JoinColumn(name = "CONCERT_ID")
    public Concert concertId;

    private String hallNm;


}
