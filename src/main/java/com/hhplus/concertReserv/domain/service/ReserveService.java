package com.hhplus.concertReserv.domain.service;

import com.hhplus.concertReserv.domain.dto.ReserveDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ReserveService {

    public ReserveDto findReserveAvailable(String authorization) {
        return new ReserveDto();
    }



    public ReserveDto applySeat(UUID uuid, LocalDateTime localDateTime, UUID uuid1) {
        return new ReserveDto();
    }
}
