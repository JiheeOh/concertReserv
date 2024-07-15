package com.hhplus.concertReserv.domain.concert.entity;

import com.hhplus.concertReserv.domain.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Seat extends BaseEntity {

    @EmbeddedId
    private SeatPK seatPk;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "CONCERT_DT",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)),
            @JoinColumn(name = "CONCERT_ID",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    })
    private ConcertSchedule concertSchedule;

    private Long seatNo;

    private String seatClass;

    private Long price;

}
