package com.hhplus.concertReserv.interfaces.controller;

import com.hhplus.concertReserv.application.PointCommand;
import com.hhplus.concertReserv.application.PointFacade;
import com.hhplus.concertReserv.domain.member.dto.PointDto;
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

@Tag(name="PointController", description = "포인트 조회, 충전 관련 처리")
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
    @Operation(summary = "사용자의 포인트 조회", description = "등록된 유저의 포인트를 조회")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success", content = {@Content(schema = @Schema(implementation = PointDto.class))})
            , @ApiResponse(responseCode = "404", description = "Not Found")})
    @Parameters({@Parameter(name = "memberId", description = "유저 ID", example = "550e8400-e29b-41d4-a716-446655440000")})
    public ResponseEntity<Object> getPoint(@RequestHeader UUID memberId) {
        return ResponseEntity.ok().body(pointFacade.getPoint(memberId));
    }

    /**
     * 사용자 UUID 기반 포인트 충전 기능
     * 토큰 안에는 사용자 UUID, 콘서트 UUID, 만료시간 정보가 있다.
     * requestBody에는 사용자 UUID, 충전하려는 금액이 있다.
     * @param requestBody requestBody
     * @return 충전하고 난 다음의 포인트
     */
    @PatchMapping("/charge")
    @Operation(summary = "사용자의 포인트 충전", description = "등록된 유저의 포인트를 충전")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success", content = {@Content(schema = @Schema(implementation = PointDto.class))})
            , @ApiResponse(responseCode = "404", description = "Not Found")})
    public ResponseEntity<Object> charge(@RequestBody PointCommand.Charge requestBody) {
        return ResponseEntity.ok().body(pointFacade.charge(requestBody));
    }


}
