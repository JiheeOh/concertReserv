package com.hhplus.concertReserv.domain.member.service;

import com.hhplus.concertReserv.domain.concert.SeatEnum;
import com.hhplus.concertReserv.domain.member.dto.PointDto;
import com.hhplus.concertReserv.domain.member.entity.Member;
import com.hhplus.concertReserv.domain.member.repositories.MemberRepository;
import com.hhplus.concertReserv.domain.reservation.entity.Payment;
import com.hhplus.concertReserv.domain.reservation.repositories.PaymentRepository;
import com.hhplus.concertReserv.exception.InvalidAmountException;
import com.hhplus.concertReserv.exception.UserNotFoundException;
import com.hhplus.concertReserv.interfaces.presentation.ErrorCode;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class PointService {
    private final PaymentRepository paymentRepository;

    private final MemberRepository memberRepository;

    @PersistenceContext
    private EntityManager entityManager;


    public PointService(PaymentRepository paymentRepository, MemberRepository memberRepository) {
        this.paymentRepository = paymentRepository;
        this.memberRepository = memberRepository;
    }

    /**
     * 포인트 충전 기능
     *
     * @param memberId 유저ID
     * @param amount   충전할 포인트
     * @return 충전후 포인트
     */
    public PointDto charge(UUID memberId, Long amount) {
        PointDto pointDto = new PointDto();
        log.info(String.format(" ==== charge() start : charge amount = %d ====", amount));

        if (amount < 0) {
            throw new InvalidAmountException(ErrorCode.INVALID_AMOUNT);
        }
        // 1. 사용자 조회
        Member member = memberRepository.findMember(memberId).orElseThrow(() -> new UserNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        member.setPoint(member.getPoint() + amount);

        // 2. 포인트 충전
        Member result = memberRepository.save(member);
        pointDto.setPoint(result.getPoint());
        log.info(String.format(" ==== charge() end : charge result = %d ==== ", pointDto.getPoint()));


        return pointDto;
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
            Member member = memberRepository.findMember(payment.getReservation().getMember().getMemberId()).orElseThrow(() -> new UserNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
            if (member.getPoint() <= 0) {
                throw new InvalidAmountException(ErrorCode.NOT_ENOUGH_AMOUNT);
            }
            member.setPoint(member.getPoint() - amount);
            memberRepository.save(member);

            // 3. 자리 확정 처리
            payment.getReservation().setConfirmYn("Y");
            payment.getReservation().getSeat().setStatus(SeatEnum.OCCUPIED.getStatus());

            paymentRepository.saveAndFlush(payment);

            // 4. return 값 생성
            pointDto.setPoint(member.getPoint());
            pointDto.setMemberId(member.getMemberId());
            pointDto.setConcertId(payment.getReservation().getSeat().getConcertSchedule().getConcertId().getConcertId());
            log.info(String.format(" ==== paid() end : All paid result : %d ====", pointDto.getPoint()));

        } else { // 분납인 경우
            // 1.포인트 차감
            Member member = payment.getReservation().getMember();
            if (member.getPoint() <= 0) {
                throw new InvalidAmountException(ErrorCode.NOT_ENOUGH_AMOUNT);
            }
            member.setPoint(member.getPoint() - amount);
            memberRepository.save(member);

            // 2.결제 완료 처리
            payment.setActuAmount(payment.getActuAmount() + amount);
            paymentRepository.saveAndFlush(payment);

            // 4. return 값 생성
            pointDto.setPoint(member.getPoint());
            log.info(String.format(" ==== paid() end : Not all paid result : %d ====", pointDto.getPoint()));
        }
        return pointDto;
    }

    /**
     * 사용자의 포인트 조회
     *
     * @param memberId 사용자 Id
     * @return 사용자의 잔여 포인트
     */
    public PointDto getPoint(UUID memberId) {
        PointDto pointDto = new PointDto();
        log.info(" ==== getPoint() start ====");
        try {
            Member member = memberRepository.findMember(memberId).orElseThrow(() -> new UserNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
            pointDto.setPoint(member.getPoint());
            log.info(String.format(" ==== getPoint() end : %d ====", pointDto.getPoint()));
        } catch (Exception e) {
            log.error(e.toString());
            pointDto.setMessage(e.toString());
            pointDto.setResult(false);
        }
        return pointDto;
    }
}
