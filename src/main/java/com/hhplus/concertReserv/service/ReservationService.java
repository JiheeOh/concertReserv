package com.hhplus.concertReserv.service;

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



    public ReservationService(SeatRepository seatRepository,
                              ReservationRepository reservationRepository,
                              PaymentRepository paymentRepository,
                              MemberRepository memberRepository) {
        this.seatRepository = seatRepository;
        this.reservationRepository = reservationRepository;
        this.paymentRepository = paymentRepository;
        this.memberRepository = memberRepository;
    }

    //TODO : 콘서트 목록 조회, 콘서트 별로 예약가능한 날짜 조회 기능이 필요하다 -> 결제 기능에서는 실패가 제일 중요하다 성공보다
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
     * @param seatId   좌석 ID
     * @return 결제 ID와 결제 만료 시간
     */
    public ReserveDto applySeat(UUID memberId, UUID seatId) {
        ReserveDto resultDto = new ReserveDto();

        try {
            // 1. 자리가 배정되어있는지 , 등록된 사용자인지 확인
            Seat seat = seatRepository.findSeat(seatId).orElseThrow(() -> new OccupiedSeatException("Seat already occupied", 500));

            Member member = memberRepository.findMember(memberId).orElseThrow(() -> new UserNotFoundException("Invalid memberId", 500));

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
        } catch (ConstraintViolationException e) { // 체크한 이후에 들어갔을 경우 대비
            log.error("=== Seat occupied recently ===");
            resultDto.setResult(false);
            resultDto.setMessage("Seat occupied recently");
        } catch (Exception e) {
            log.error(e.toString());
            resultDto.setResult(false);
            resultDto.setMessage(e.toString());
        }

        return resultDto;
    }


}
