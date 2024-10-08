package com.hhplus.concertReserv.domain.concert.entity;

import com.hhplus.concertReserv.domain.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@IdClass(ConcertSchedulePK.class)
@Table(name="concert_schedule", indexes = @Index(name = "idx_del_Yn", columnList = "DEL_YN"))
public class ConcertSchedule extends BaseEntity {

    @Id
    @Column(name = "CONCERT_DT")
    private LocalDateTime concertDt;

    @Id
    @ManyToOne
    @JoinColumn(name = "CONCERT_ID")
    public Concert concertId;

    private String hallNm;

    private String delYn;

}
