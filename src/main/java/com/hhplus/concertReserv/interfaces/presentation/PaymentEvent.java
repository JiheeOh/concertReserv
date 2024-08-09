package com.hhplus.concertReserv.interfaces.presentation;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentEvent {
        private String payYn;
        private Long actuAmount;
        private String confirmYn;
        private String status;
}
