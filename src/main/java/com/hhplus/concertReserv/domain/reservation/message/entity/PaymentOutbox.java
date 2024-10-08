package com.hhplus.concertReserv.domain.reservation.message.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Getter
@Setter
@Table(name= "PAYMENT_OUTBOX" ,indexes = @Index(name = "idx_payment_id", columnList = "PAYMENT_ID"))
public class PaymentOutbox {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)")
    private UUID eventId;

    private String message;
    private String status;

    private UUID paymentId;
    private LocalDateTime eventCreateDt;
    private LocalDateTime eventCompletedDt;

    @ColumnDefault("0")
    private Long retry;

}
