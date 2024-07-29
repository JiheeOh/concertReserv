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
//@Table(name = "RESERVATION", uniqueConstraints = {
//        @UniqueConstraint(
//                name = "SEAT_ID_STATUS_UNIQUE",
//                columnNames = {"SEAT_ID", "STATUS"}
//        )
//}) Unique key로 동시성 제어할 때 필요
public class Reservation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID reservationId;

    @ManyToOne
    @JoinColumn(name = "SEAT_ID", referencedColumnName = "seatId", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Seat seat;

    private String confirmYn;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

//    Unique key로 동시성 제어할 때 필요
//    private String status;





}
