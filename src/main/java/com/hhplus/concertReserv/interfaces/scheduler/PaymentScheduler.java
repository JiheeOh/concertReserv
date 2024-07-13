package com.hhplus.concertReserv.interfaces.scheduler;

import com.hhplus.concertReserv.domain.entity.Payment;
import com.hhplus.concertReserv.domain.entity.Reservation;
import com.hhplus.concertReserv.domain.entity.Seat;
import com.hhplus.concertReserv.domain.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Slf4j
public class PaymentScheduler {

    private final SeatRepository seatRepository;
    private final ReservationRepository reservationRepository;
    private final PaymentRepository paymentRepository;

    private final TokenRepository tokenRepository;


    public PaymentScheduler(SeatRepository seatRepository,ReservationRepository reservationRepository,PaymentRepository paymentRepository,TokenRepository tokenRepository){
        this.seatRepository = seatRepository;
        this.reservationRepository = reservationRepository;
        this.paymentRepository = paymentRepository;
        this.tokenRepository = tokenRepository;

    }

    /**
     *
     * 스케줄러로 결제 시간 만료 처리
     * 4분마다 작동
     * 1. 결제 마감 시간이 지난 결제Id 조회
     * 2. 결제 ID에 해당되는 token id 만료 처리
     * 3. 자리 반납 처리
     * 4. 예약 반납 처리
     */
    @Scheduled(cron = "0 0/4 * * * *")
    private void expiredNotPaidToken() {
        log.info("========== Expire not paid token ==========");

        // 1. 결제 마감 시간 지난 결제 ID 조회
        List<Payment> payment = paymentRepository.findNotPaidToken();

        // 2. 토큰 만료 처리
        List<Long> tokenIds = payment.stream().map(Payment::getTokenId).toList();
        tokenRepository.deactivateNotPaidToken(tokenIds);

        // 3. 자리 반납 처리
        List<Reservation> reservation = payment.stream().map(Payment::getReservation).toList();
        List<Seat> seat= reservation.stream().map(Reservation::getSeat).toList();
        seat.forEach(x -> x.getSeatPk().setStatus(null));
        seatRepository.updateStatus(seat);

        // 4. 예약 ID 삭제 처리
        reservationRepository.deleteExpired(reservation);

    }
}
