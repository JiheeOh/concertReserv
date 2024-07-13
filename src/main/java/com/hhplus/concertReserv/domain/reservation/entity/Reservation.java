package com.hhplus.concertReserv.domain.reservation.entity;

import com.hhplus.concertReserv.domain.concert.entity.Seat;
import com.hhplus.concertReserv.domain.common.entity.BaseEntity;
import com.hhplus.concertReserv.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name="RESERVATION", uniqueConstraints = {
        @UniqueConstraint(
                name = "SEAT_ID_STATUS_UNIQUE",
                columnNames = {"SEAT_ID","STATUS"}
        )
})
public class Reservation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID reservationId;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name="SEAT_ID", referencedColumnName = "seatId" ,foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)),
            @JoinColumn(name="STATUS",referencedColumnName = "status",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    })
    private Seat seat;

    private String confirmYn;

    @ManyToOne
    @JoinColumn(name="MEMBER_ID", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;





}
