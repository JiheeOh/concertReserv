package com.hhplus.concertReserv.domain.reservation.kafka.outbox.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name= "RESERVATION_OUTBOX" ,indexes = @Index(name = "idx_seat_user_id", columnList = "SEAT_ID,USER_ID"))
public class ReservationOutbox {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)")
    private UUID eventId;

    private String message;
    private String status;
    private Long retry;

    private UUID seatId;
    private UUID userId;
    private LocalDateTime eventCreateDt;
    private LocalDateTime eventCompletedDt;
}
