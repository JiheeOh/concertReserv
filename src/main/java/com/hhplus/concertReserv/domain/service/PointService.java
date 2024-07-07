package com.hhplus.concertReserv.domain.service;

import com.hhplus.concertReserv.domain.dto.PointDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PointService {
    public PointDto charge(UUID uuid, Long amount) {
        return new PointDto();
    }

    public PointDto paid(UUID uuid, LocalDateTime localDateTime, Long amount) {
        return new PointDto();
    }

    public PointDto getPoint(UUID memberId) {
        return new PointDto();
    }
}
