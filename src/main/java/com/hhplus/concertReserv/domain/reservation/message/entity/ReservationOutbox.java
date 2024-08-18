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
@Table(name= "RESERVATION_OUTBOX" ,indexes = @Index(name = "idx_seat_user_id", columnList = "SEAT_ID,USER_ID"))
public class ReservationOutbox {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)")
    private UUID eventId;

    private String message;
    private String status;

    @ColumnDefault("0")
    private Long retry;

    private UUID seatId;
    private UUID userId;
    private LocalDateTime eventCreateDt;
    private LocalDateTime eventCompletedDt;
}
