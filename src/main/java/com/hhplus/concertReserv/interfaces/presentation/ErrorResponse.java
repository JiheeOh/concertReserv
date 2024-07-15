package com.hhplus.concertReserv.interfaces.presentation;

import org.springframework.http.HttpStatus;

public record ErrorResponse(
        HttpStatus code,
        String message
) {
}
