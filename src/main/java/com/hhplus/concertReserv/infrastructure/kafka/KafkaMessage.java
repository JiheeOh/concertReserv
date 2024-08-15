package com.hhplus.concertReserv.infrastructure.kafka;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KafkaMessage<T> {
    private LocalDateTime publishDt;
    private LocalDateTime eventCreateDt;
    private String eventKey;
    private T payload;
}
