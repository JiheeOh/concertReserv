package com.hhplus.concertReserv.domain.reservation.service;

import com.hhplus.concertReserv.domain.concert.SeatEnum;
import com.hhplus.concertReserv.domain.concert.dto.ConcertDto;
import com.hhplus.concertReserv.domain.concert.dto.SeatDto;
import com.hhplus.concertReserv.domain.member.dto.PointDto;
import com.hhplus.concertReserv.domain.member.entity.Users;
import com.hhplus.concertReserv.domain.member.repositories.MemberRepository;
import com.hhplus.concertReserv.domain.reservation.dto.PaymentDto;
import com.hhplus.concertReserv.domain.reservation.dto.ReserveDto;
import com.hhplus.concertReserv.domain.reservation.dto.ReserveInfoDto;
import com.hhplus.concertReserv.domain.reservation.entity.Payment;
import com.hhplus.concertReserv.domain.reservation.event.PaymentEvent;
import com.hhplus.concertReserv.domain.reservation.repositories.PaymentRepository;
import com.hhplus.concertReserv.exception.InvalidAmountException;
import com.hhplus.concertReserv.exception.UserNotFoundException;
import com.hhplus.concertReserv.domain.common.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final MemberRepository memberRepository;


    public PaymentService(PaymentRepository paymentRepository, MemberRepository memberRepository){
        this.paymentRepository = paymentRepository;
        this.memberRepository = memberRepository;
    }

    public ReserveDto createPayment(ReserveInfoDto infoDto) {
        ReserveDto resultDto = new ReserveDto();
        try {
            // 결제 정보 생성
            Payment payment = new Payment();
            payment.setPayYn("N");
            payment.setPrice(infoDto.getReservation().getSeat().getPrice());
            payment.setDueTime(LocalDateTime.now().plusMinutes(5));
            payment.setReservation(infoDto.getReservation());
            payment.setActuAmount(0L);

            Payment afterPayment = paymentRepository.saveAndFlush(payment);
            PaymentDto paymentDto = new PaymentDto(afterPayment);


            // 예약된 좌석 정보 반환
            List<SeatDto> seatDtoList = new ArrayList<>();
            seatDtoList.add(new SeatDto(afterPayment.getReservation().getSeat()));
            ConcertDto concertDto = new ConcertDto(infoDto.getReservation().getSeat());
            concertDto.setSeat(seatDtoList);


            // 결과값 반환
            resultDto.setPayment(paymentDto);
            resultDto.setConcert(concertDto);

        }catch (Exception e){
            log.error(e.toString());
            resultDto.setMessage(e.toString());
            resultDto.setResult(false);

        }
        return resultDto;
    }



    @Transactional
    public PointDto paid(UUID paymentId, Long amount) {
        PointDto pointDto = new PointDto();


        log.info(String.format(" ==== paid() start : request amount %d ", amount));

        if (amount < 0) {
            throw new IllegalArgumentException("Invalid amount");
        }
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new InvalidAmountException(ErrorCode.INVALID_AMOUNT));

        if (payment.getActuAmount() + amount == payment.getPrice()) { // 완납 경우
            // 1. 결제 완료 처리
            payment.setPayYn("Y");
            payment.setActuAmount(payment.getActuAmount() + amount);

            // 2. 포인트 차감 처리
            Users member = memberRepository.findMember(payment.getReservation().getMember().getUserId()).orElseThrow(() -> new UserNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
            if (member.getUserPoint() <= 0) {
                throw new InvalidAmountException(ErrorCode.NOT_ENOUGH_AMOUNT);
            }
            member.setUserPoint(member.getUserPoint() - amount);
            memberRepository.save(member);

            // 3. 자리 확정 처리
            payment.getReservation().setConfirmYn("Y");
            payment.getReservation().getSeat().setStatus(SeatEnum.OCCUPIED.getStatus());

            paymentRepository.saveAndFlush(payment);

            // 결제 정보 publish 추가
            PaymentEvent paymentEvent = PaymentEvent.builder()
                    .payYn("Y")
                    .confirmYn("Y")
                    .actuAmount(payment.getActuAmount() + amount)
                    .status(SeatEnum.OCCUPIED.getStatus())
                    .paymentId(paymentId).build();

            pointDto.setPaymentEvent(paymentEvent);

            // 4. return 값 생성
            pointDto.setPoint(member.getUserPoint());
            pointDto.setMemberId(member.getUserId());
            pointDto.setConcertId(payment.getReservation().getSeat().getConcertSchedule().getConcertId().getConcertId());
            log.info(String.format(" ==== paid() end : All paid result : %d ====", pointDto.getPoint()));

        } else { // 분납인 경우
            // 1.포인트 차감
            Users member = payment.getReservation().getMember();
            if (member.getUserPoint() <= 0) {
                throw new InvalidAmountException(ErrorCode.NOT_ENOUGH_AMOUNT);
            }
            member.setUserPoint(member.getUserPoint() - amount);
            memberRepository.save(member);

            // 2.결제 완료 처리
            payment.setActuAmount(payment.getActuAmount() + amount);
            paymentRepository.saveAndFlush(payment);

            // 결제 정보 publish 추가
            PaymentEvent paymentEvent = PaymentEvent.builder()
                    .paymentId(paymentId)
                    .actuAmount(payment.getActuAmount() + amount).build();
            pointDto.setPaymentEvent(paymentEvent);

            // 4. return 값 생성
            pointDto.setPoint(member.getUserPoint());
            log.info(String.format(" ==== paid() end : Not all paid result : %d ====", pointDto.getPoint()));
        }
        return pointDto;
    }
}
