package com.hhplus.concertReserv.domain.service;

import com.hhplus.concertReserv.domain.dto.ConcertDto;
import com.hhplus.concertReserv.domain.dto.PaymentDto;
import com.hhplus.concertReserv.domain.dto.ReserveDto;
import com.hhplus.concertReserv.domain.dto.SeatDto;
import com.hhplus.concertReserv.domain.entity.Member;
import com.hhplus.concertReserv.domain.entity.Payment;
import com.hhplus.concertReserv.domain.entity.Reservation;
import com.hhplus.concertReserv.domain.entity.Seat;
import com.hhplus.concertReserv.domain.repository.*;
import com.hhplus.concertReserv.exception.OccupiedSeatException;
import com.hhplus.concertReserv.exception.UserNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ReservationService {
    private final SeatRepository seatRepository;
    private final ReservationRepository reservationRepository;
    private final PaymentRepository paymentRepository;

    private final MemberRepository memberRepository;

    private final TokenRepository tokenRepository;


    public ReservationService(SeatRepository seatRepository,
                              ReservationRepository reservationRepository,
                              PaymentRepository paymentRepository,
                              MemberRepository memberRepository,
                              TokenRepository tokenRepository) {
        this.seatRepository = seatRepository;
        this.reservationRepository = reservationRepository;
        this.paymentRepository = paymentRepository;
        this.memberRepository = memberRepository;
        this.tokenRepository = tokenRepository;
    }

    public ReserveDto findReserveAvailable(UUID concertId) {
        ReserveDto dto = new ReserveDto();
        try {
            List<Seat> seats = seatRepository.findReserveAvailable(concertId);

            // 결과값 DTO로 변환
            ConcertDto concertDto = new ConcertDto(seats.get(0)); //모든 좌석은 같은 concertId를 가진다.
            List<SeatDto> seatDtos = seats.stream().map(SeatDto::new).toList();
            concertDto.setSeat(seatDtos);
            dto.setConcert(concertDto);

        } catch (Exception e) {
            log.error(e.toString());
        }

        return dto;
    }

    /**
     * 좌석 임시 배정 처리 기능
     * 예약 테이블에서 좌석이 배정되어있는지 확인 후 insert 시도
     * 시도 후 유니크키 중복이 발생하면 동시성 문제가 발생한 것
     * 좌석은 5분동안 임시 배정
     *
     * @param memberId 유저 ID
     * @param seatId 좌석 ID
     * @return 결제 ID와 결제 만료 시간
     */
    public ReserveDto applySeat(UUID memberId, UUID seatId) {
        ReserveDto resultDto= new ReserveDto();

        try {
            // 1. 자리가 배정되어있는지 , 등록된 사용자인지 확인
            Seat seat = seatRepository.findSeat(seatId).orElseThrow(() -> new OccupiedSeatException("Seat already occupied",500));

            Member member = memberRepository.findMember(memberId).orElseThrow(()-> new UserNotFoundException("Invalid memberId",500));

            //2. 자리 임시 배정 처리 ( RESEVERED )
            Reservation reservation = new Reservation();
            // TODO: ENUM 처리 할 것
            seat.getSeatPk().setStatus("RESERVED");
            reservation.setSeat(seat);
            reservation.setMember(member);
            reservation.setConfirmYn("N");

            Reservation afterReservation = reservationRepository.save(reservation);

            //3. 결제 ID 생성해서 반환
            Payment payment = new Payment();
            payment.setPayYn("N");
            payment.setPrice(seat.getPrice());
            payment.setDueTime(LocalDateTime.now().plusMinutes(5));
            payment.setReservation(afterReservation);

            Payment afterPayment = paymentRepository.save(payment);
            PaymentDto paymentDto = new PaymentDto();
            paymentDto.setDueTime(afterPayment.getDueTime());
            paymentDto.setPayId(paymentDto.getPayId());

        } catch (OccupiedSeatException e) { // 체크 당시 이미 자리가 차있을 경우
            log.error("=== Seat already occupied ===");
            resultDto.setResult(false);
            resultDto.setMessage(e.toString());
        } catch (ConstraintViolationException e){ // 체크한 이후에 들어갔을 경우 대비
            log.error("=== Seat occupied recently ===");
            resultDto.setResult(false);
            resultDto.setMessage("Seat occupied recently");
        }catch (Exception e){
            log.error(e.toString());
            resultDto.setResult(false);
            resultDto.setMessage(e.toString());
        }

        return resultDto;
    }


    /**
     *
     * 스케줄러로 결제 시간 만료 처리
     * 4분마다 작동
     * 1. 결제 마감 시간이 지난 결제Id 조회
     * 2. 결제 ID에 해당되는 token id 만료 처리
     * 3. 자리 반남 처리
     * 4. 예약 반남 처리
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
