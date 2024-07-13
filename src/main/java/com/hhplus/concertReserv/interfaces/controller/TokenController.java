package com.hhplus.concertReserv.interfaces.controller;

import com.hhplus.concertReserv.application.TokenCommand;
import com.hhplus.concertReserv.application.TokenFacade;
import com.hhplus.concertReserv.domain.token.dto.TokenDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name= "TokenController", description = "토큰 발급, 조회 처리")
@RestController
@RequestMapping("/token")
public class TokenController {
    private final TokenFacade tokenFacade;

    public TokenController(TokenFacade tokenFacade) {
        this.tokenFacade = tokenFacade;
    }

    /**
     * 대기열 토큰을 생성
     * requestBody에 콘서트 UUID와 사용자 UUID 정보가 있다.
     * @param tokenRequest requestBody
     * @return 대기열 토큰
     */
    @PostMapping("/create")
    @Operation(summary = "대기열 토큰 발급 요청", description = "대기열에 등록하려는 사용자에게 신청하려는 콘서트에 해당하는 토큰을 발급")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",description = "Success",content = {@Content(schema = @Schema(implementation = TokenDto.class))})
                            ,@ApiResponse(responseCode = "404", description = "Not Found"),
    })
    public ResponseEntity<Object> getToken(@RequestBody TokenCommand.CreateToken tokenRequest) {
        return ResponseEntity.ok().body(tokenFacade.getToken(tokenRequest));

    }

    /**
     * 대기열 토큰의 상태 조회
     * tokenId
     * @param tokenId 대기열에 등록된 tokenId
     * @return 대기열 토큰
     */
    @PostMapping("/checkActivate")
    @Operation(summary = "대기열 토큰의 상태 확인 요청", description = "대기열에 등록된 토큰이 활성화 상태인지 조회")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",description = "Success",content = {@Content(schema = @Schema(implementation = TokenDto.class))})
            ,@ApiResponse(responseCode = "404", description = "Not Found"),
    })
    @Parameter(name = "tokenId", description = "토큰 ID", example = "550e8400-e29b-41d4-a716-446655440000")
    public ResponseEntity<Object> findActivateToken(@RequestParam Long tokenId) {
        return ResponseEntity.ok().body(tokenFacade.findActivateToken(tokenId));

    }

}
