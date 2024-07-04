package com.hhplus.concertReserv.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class PaymentDto {
    UUID payId;
    LocalDateTime dueTime;
}
