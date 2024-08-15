package com.hhplus.concertReserv.domain.reservation.event;


import lombok.*;

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
}
