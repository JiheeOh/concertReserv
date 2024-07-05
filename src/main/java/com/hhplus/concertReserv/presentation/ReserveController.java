package com.hhplus.concertReserv.presentation;

import com.hhplus.concertReserv.application.ReserveCommand;
import com.hhplus.concertReserv.application.ReserveFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/reserve")
public class ReserveController {
    private final ReserveFacade reserveFacade;

    public ReserveController(ReserveFacade reserveFacade) {
        this.reserveFacade = reserveFacade;
    }

    /**
     * 토큰을 받아서 예약이 가능한 날짜와 좌석 정보를 반환한다.
     * 토큰 안에는 유저 Id, 콘서트 Id 정보가 있다.
     * @param authorization jwt token
     * @return 예약가능한 날짜와 좌석 정보
     */
    @GetMapping("/available")
    public ResponseEntity<Object> reserveAvailable(@RequestHeader String authorization) {
        return ResponseEntity.ok().body(reserveFacade.findReserveAvailable(authorization));
    }

    /**
     * 토큰,예약하려는 날짜와 좌석 정보를 받아 임시배정 처리
     * 토큰 안에는 유저 Id, 콘서트 Id 정보가 있다.
     * 예약하려는 날짜와 좌석 정보는 RequestBody에 있다.
     * @param authorization jwt token
     * @param requestBody requestBody
     * @return 예약하려는 좌석의 가격 정보
     */
    @PostMapping("/applySeat")
    public ResponseEntity<Object> applySeat(@RequestHeader String authorization, @RequestBody ReserveCommand.ApplySeat requestBody) {
        return ResponseEntity.ok().body(reserveFacade.applySeat(authorization,requestBody));
    }
}
