package com.hhplus.concertReserv.presentation;

import org.springframework.http.HttpStatus;

public record ErrorResponse(
        HttpStatus code,
        String message
) {
}