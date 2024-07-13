package com.hhplus.concertReserv.interfaces.controller;

import com.hhplus.concertReserv.application.ReserveCommand;
import com.hhplus.concertReserv.application.ReserveFacade;
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

import java.util.UUID;


@Tag(name = "ReserveController", description = "예약 요청 및 예약 가능 날짜/좌석 조회 처리")
@RestController
@RequestMapping("/reserve")
public class ReserveController {
    private final ReserveFacade reserveFacade;

    public ReserveController(ReserveFacade reserveFacade) {
        this.reserveFacade = reserveFacade;
    }

    /**
     * 토큰을 받아서 예약이 가능한 날짜와 좌석 정보를 반환한다.
     * 콘서트 Id, token Id 정보를 UI 에서 받아야한다.
     *
     * @param concertId 콘서트 아이디
     * @return 예약가능한 날짜와 좌석 정보
     */
    @GetMapping("/available")
    @Operation(summary = "예약가능한 날짜와 좌석 정보 반환", description = "신청하려는 콘서트의 예약 가능한 날짜와 좌석 정보를 반환")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success", content = {@Content(schema = @Schema(implementation = ReserveDto.class))})
            , @ApiResponse(responseCode = "404", description = "Not Found")})
    @Parameters({@Parameter(name = "tokenId", description = "토큰 ID", example = "1"),
            @Parameter(name = "concertId", description = "콘서트 ID", example = "9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d")})
    public ResponseEntity<Object> reserveAvailable(@RequestHeader Long tokenId , @RequestParam UUID concertId) {
        return ResponseEntity.ok().body(reserveFacade.findReserveAvailable(concertId, tokenId));
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
}
