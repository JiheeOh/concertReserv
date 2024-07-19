package com.hhplus.concertReserv.interfaces.presentation;



public record ErrorResponse<T>(
       int status,
       String resultMsg

) {
}
