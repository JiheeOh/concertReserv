package com.hhplus.concertReserv.domain.reservation.event;


import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentEvent {
        private String payYn;
        private Long actuAmount;
        private String confirmYn;
        private String status;
        private UUID paymentId;
}
