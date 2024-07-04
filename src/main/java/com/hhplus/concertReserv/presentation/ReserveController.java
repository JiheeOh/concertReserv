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

    @GetMapping("/available")
    public ResponseEntity<Object> reserveAvailable(@RequestHeader String authorization) {
        return ResponseEntity.ok().body(reserveFacade.findReserveAvailable(authorization));
    }

    @PostMapping("/applySeat")
    public ResponseEntity<Object> applySeat(@RequestHeader String authorization, @RequestBody ReserveCommand.ApplySeat requestBody) {
        return ResponseEntity.ok().body(reserveFacade.applySeat(authorization,requestBody));
    }
}
