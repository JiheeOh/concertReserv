package com.hhplus.concertReserv.domain.concert.entity;

import com.hhplus.concertReserv.domain.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name ="seat", indexes = @Index(name="idx_seat_covering",columnList = "concert_id, concert_dt, seat_Id,seat_NO,seat_class,status"))
public class Seat extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column( columnDefinition = "BINARY(16)")
    private UUID seatId;

    private String status;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "CONCERT_DT",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT) ,insertable = false, updatable = false),
            @JoinColumn(name = "CONCERT_ID",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), insertable = false, updatable = false)
    })
    private ConcertSchedule concertSchedule;

    private Long seatNo;

    private String seatClass;

    private Long price;

    // 낙관적 락일 때 필요
//    @Version
//    private Long version;

}
