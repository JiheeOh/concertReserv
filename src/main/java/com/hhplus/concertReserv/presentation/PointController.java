package com.hhplus.concertReserv.presentation;

import com.hhplus.concertReserv.application.PointCommand;
import com.hhplus.concertReserv.application.PointFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/point")
public class PointController {
    private final PointFacade pointFacade;

    public PointController(PointFacade pointFacade) {
        this.pointFacade = pointFacade;
    }


    @PatchMapping("/charge")
    public ResponseEntity<Object> charge(@RequestHeader String authorization, @RequestBody PointCommand.Charge requestBody) {
        return ResponseEntity.ok().body(pointFacade.charge(authorization,requestBody));
    }

    @PostMapping("/paid")
    public ResponseEntity<Object> paid(@RequestHeader String authorization, @RequestBody PointCommand.Paid requestBody) {
        return ResponseEntity.ok().body(pointFacade.paid(authorization,requestBody));
    }
}
