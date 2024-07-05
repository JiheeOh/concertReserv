package com.hhplus.concertReserv.presentation;

import com.hhplus.concertReserv.application.PointCommand;
import com.hhplus.concertReserv.application.PointFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/point")
public class PointController {
    private final PointFacade pointFacade;

    public PointController(PointFacade pointFacade) {
        this.pointFacade = pointFacade;
    }


    /**
     * 사용자 UUID로 사용자의 포인트 조회
     * @param memberId 사용자 UUID
     * @return 사용자 포인트
     */
    @GetMapping("/find")
    public ResponseEntity<Object> getPoint(@RequestParam UUID memberId) {
        return ResponseEntity.ok().body(pointFacade.getPoint(memberId));
    }

    /**
     * 사용자 UUID 기반 포인트 충전 기능
     * 토큰 안에는 사용자 UUID, 콘서트 UUID, 만료시간 정보가 있다.
     * requestBody에는 사용자 UUID, 충전하려는 금액이 있다.
     * @param authorization jwtToken
     * @param requestBody requestBody
     * @return 충전하고 난 다음의 포인트
     */
    //TODO : 토큰이 꼭 있어야하는가..?
    @PatchMapping("/charge")
    public ResponseEntity<Object> charge(@RequestHeader String authorization, @RequestBody PointCommand.Charge requestBody) {
        return ResponseEntity.ok().body(pointFacade.charge(authorization,requestBody));
    }

    /**
     * 임시배정된 자리를 결제
     * 토큰 안에 만료시간, 콘서트 UUID, 사용자 UUID 가 있다.
     * requestBody 안에 결제 UUID가 있다. 결제 식별자로 좌석 정보를 알 수 있다.
     * @param authorization jwtToken
     * @param requestBody requestBody
     * @return 결제하고 나고 남은 포인트
     */
    @PostMapping("/paid")
    public ResponseEntity<Object> paid(@RequestHeader String authorization, @RequestBody PointCommand.Paid requestBody) {
        return ResponseEntity.ok().body(pointFacade.paid(authorization,requestBody));
    }
}
