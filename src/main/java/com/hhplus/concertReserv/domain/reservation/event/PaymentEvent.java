package com.hhplus.concertReserv.domain.reservation.event;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaymentEvent {
        private String payYn;
        private Long actuAmount;
        private String confirmYn;
        private String status;
}
