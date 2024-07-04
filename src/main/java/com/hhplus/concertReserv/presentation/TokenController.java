package com.hhplus.concertReserv.presentation;

import com.hhplus.concertReserv.application.TokenCommand;
import com.hhplus.concertReserv.application.TokenFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
public class TokenController {
    private final TokenFacade tokenFacade;

    public TokenController(TokenFacade tokenFacade) {
        this.tokenFacade = tokenFacade;
    }

    @PostMapping("/wait")
    public ResponseEntity<Object> getToken(@RequestBody TokenCommand.CreateToken tokenRequest) {
        return ResponseEntity.ok().body(tokenFacade.getToken(tokenRequest));

    }
}
