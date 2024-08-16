package com.hhplus.concertReserv.application;

import com.hhplus.concertReserv.domain.member.dto.PointDto;
import com.hhplus.concertReserv.domain.reservation.event.PaymentEventPublisher;
import com.hhplus.concertReserv.domain.reservation.service.PaymentService;
import com.hhplus.concertReserv.domain.token.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PaymentFacade {

    private final PaymentService paymentService;

    private final TokenService tokenService;

    private final PaymentEventPublisher paymentEventPublisher;

    public PaymentFacade(PaymentService paymentService, TokenService tokenService, PaymentEventPublisher paymentEventPublisher) {
        this.paymentService = paymentService;
        this.tokenService = tokenService;
        this.paymentEventPublisher = paymentEventPublisher;
    }

    public PointDto paid(PointCommand.Paid requestBody) {
        PointDto result = new PointDto();

        boolean paidSuccess = false;
        int retryCount = 0;

        while (!paidSuccess && retryCount < 10000) {
            retryCount += 1;
            try {
                result = paymentService.paid(requestBody.paymentId(), requestBody.amount());
                paidSuccess = true;

                // 결제 정보 event 발행
                paymentEventPublisher.publish(result.getPaymentEvent());

                // 토큰 만료화
                tokenService.deactivateToken(result.getMemberId(), result.getConcertId());

            } catch (ObjectOptimisticLockingFailureException e) {
                log.error(e.toString());
                log.info(String.format("======= retryCount : %d, amount : %d ======", retryCount, requestBody.amount()));
            } catch (Exception e) {
                log.error(e.toString());
                break;
            }
        }
        return result;
    }
}
