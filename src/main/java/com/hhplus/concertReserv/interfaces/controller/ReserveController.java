package com.hhplus.concertReserv.interfaces.controller;

import com.hhplus.concertReserv.application.*;
import com.hhplus.concertReserv.domain.member.dto.PointDto;
import com.hhplus.concertReserv.domain.reservation.dto.ReserveDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;


@Tag(name = "ReserveController", description = "예약 요청 및 예약 가능 날짜/좌석 조회 처리")
@RestController
@RequestMapping("/reserve")
public class ReserveController {
    private final ReserveFacade reserveFacade;
    private final ConcertFacade concertFacade;

    private final PaymentFacade paymentFacade;

    public ReserveController(ReserveFacade reserveFacade, ConcertFacade concertFacade, PaymentFacade paymentFacade) {
        this.reserveFacade = reserveFacade;
        this.concertFacade = concertFacade;
        this.paymentFacade = paymentFacade;
    }

    /**
     * 해당 콘서트의 예약이 가능한 좌석 정보를 반환한다.
     *
     * @param concertId 콘서트 아이디
     * @return 예약가능한 날짜와 좌석 정보
     */
    @GetMapping("/concerts/seats")
    @Operation(summary = "콘서트 별 예약가능한 좌석 정보 반환", description = "신청하려는 콘서트의 예약 가능한  좌석 정보를 반환")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success", content = {@Content(schema = @Schema(implementation = ReserveDto.class))})
            , @ApiResponse(responseCode = "404", description = "Not Found")})
    @Parameters({@Parameter(name = "concertDt", description = "콘서트 일시", example = "2024-12-25 13:00:00.000"),
            @Parameter(name = "concertId", description = "콘서트 ID", example = "9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d")})
    public ResponseEntity<Object> reserveAvailableSeat(@RequestParam UUID concertId,@RequestParam LocalDateTime concertDt) {
        return ResponseEntity.ok().body(reserveFacade.findReserveAvailableSeat(concertId,concertDt));
    }

    /**
     * 해당 콘서트의 예약이 가능한 스케줄 정보를 반환한다.
     *
     * @param concertId 콘서트 아이디
     * @return 예약가능한 날짜와 좌석 정보
     */
    @GetMapping("/concerts/schedule")
    @Operation(summary = "콘서트 별 예약가능한 스케줄 정보 반환", description = "신청하려는 콘서트의 예약 가능한  좌석 정보를 반환")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success", content = {@Content(schema = @Schema(implementation = ReserveDto.class))})
            , @ApiResponse(responseCode = "404", description = "Not Found")})
    @Parameters(
            @Parameter(name = "concertId", description = "콘서트 ID", example = "9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d"))
    public ResponseEntity<Object> reserveAvailableSchedule(@RequestParam UUID concertId) {
        return ResponseEntity.ok().body(reserveFacade.findReserveAvailableSchedule(concertId));
    }

    /**
     * 콘서트 목록 조회
     * @return 콘서트 목록
     */
    @GetMapping("/concert")
    @Operation(summary = "콘서트 목록 조회", description = "콘서트 목록 조회 ")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success", content = {@Content(schema = @Schema(implementation = ReserveDto.class))})
            , @ApiResponse(responseCode = "404", description = "Not Found")})
    public ResponseEntity<Object> findConcertList() {
        return ResponseEntity.ok().body(concertFacade.findConcertList());
    }


    /**
     * 토큰,예약하려는 날짜와 좌석 정보를 받아 임시배정 처리
     * 토큰 안에는 유저 Id, 콘서트 Id, token Id 정보가 있다.
     * 예약하려는 날짜와 좌석 정보는 RequestBody에 있다.
     *
     * @param requestBody requestBody
     * @return 예약하려는 좌석의 가격 정보
     */
    @PostMapping("/applySeat")
    @Operation(summary = "좌석 임시 배정 처리", description = "좌석 임시 배정 처리, 가격 정보 반환")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success", content = {@Content(schema = @Schema(implementation = ReserveDto.class))})
            , @ApiResponse(responseCode = "404", description = "Not Found")})
    public ResponseEntity<Object> applySeat(@RequestBody ReserveCommand.ApplySeat requestBody) {
        return ResponseEntity.ok().body(reserveFacade.applySeat(requestBody));
    }

    /**
     * 임시배정된 자리를 결제
     * 토큰 안에 만료시간, 콘서트 UUID, 사용자 UUID 가 있다.
     * requestBody 안에 결제 UUID가 있다. 결제 식별자로 좌석 정보를 알 수 있다.
     * @param requestBody requestBody
     * @return 결제하고 나고 남은 포인트
     */
    @PostMapping("/paid")
    @Operation(summary = "임시배정된 자리를 결제 요청", description = "임시 배정된 자리의 결제 처리 완료")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success", content = {@Content(schema = @Schema(implementation = PointDto.class))})
            , @ApiResponse(responseCode = "404", description = "Not Found")})
    public ResponseEntity<Object> paid(@RequestBody PointCommand.Paid requestBody) {
        return ResponseEntity.ok().body(paymentFacade.paid(requestBody));
    }
}
