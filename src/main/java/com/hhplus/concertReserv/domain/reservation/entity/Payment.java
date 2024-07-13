package com.hhplus.concertReserv.domain.reservation.entity;

import com.hhplus.concertReserv.domain.common.entity.BaseEntity;
import com.hhplus.concertReserv.domain.reservation.entity.Reservation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Payment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID paymentId;

    private String payYn;
    private Long price;
    private Long actuAmount;
    private LocalDateTime dueTime;

    @OneToOne
    @JoinColumn(name= "RESERVATION_ID", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Reservation reservation;

    private Long tokenId;


}
