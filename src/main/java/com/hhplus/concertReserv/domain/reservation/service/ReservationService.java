package com.hhplus.concertReserv.domain.reservation.service;

import com.hhplus.concertReserv.domain.concert.SeatEnum;
import com.hhplus.concertReserv.domain.concert.dto.ConcertDto;
import com.hhplus.concertReserv.domain.concert.dto.SeatDto;
import com.hhplus.concertReserv.domain.concert.entity.Seat;
import com.hhplus.concertReserv.domain.concert.repositories.SeatRepository;
import com.hhplus.concertReserv.domain.member.entity.Member;
import com.hhplus.concertReserv.domain.member.repositories.MemberRepository;
import com.hhplus.concertReserv.domain.reservation.dto.ReserveDto;
import com.hhplus.concertReserv.domain.reservation.dto.ReserveInfoDto;
import com.hhplus.concertReserv.domain.reservation.entity.Reservation;
import com.hhplus.concertReserv.domain.reservation.repositories.ReservationRepository;
import com.hhplus.concertReserv.exception.OccupiedSeatException;
import com.hhplus.concertReserv.exception.UserNotFoundException;
import com.hhplus.concertReserv.interfaces.presentation.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ReservationService {
    private final SeatRepository seatRepository;
    private final ReservationRepository reservationRepository;

    private final MemberRepository memberRepository;


    public ReservationService(SeatRepository seatRepository,
                              ReservationRepository reservationRepository,
                              MemberRepository memberRepository) {
        this.seatRepository = seatRepository;
        this.reservationRepository = reservationRepository;
        this.memberRepository = memberRepository;
    }

    public ReserveDto findReserveAvailable(UUID concertId) {
        ReserveDto dto = new ReserveDto();
        try {
            log.info(" ==== findReserveAvailable() start ====");
            List<Seat> seats = seatRepository.findReserveAvailable(concertId);

            // 결과값 DTO로 변환
            ConcertDto concertDto = new ConcertDto(seats.get(0)); //모든 좌석은 같은 concertId를 가진다.
            List<SeatDto> seatDtos = seats.stream().map(SeatDto::new).toList();
            concertDto.setSeat(seatDtos);
            dto.setConcert(concertDto);
            log.info(" ==== findReserveAvailable() end ====");
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

    @Transactional
    public ReserveInfoDto applySeat(UUID memberId, UUID seatId) {
        ReserveInfoDto resultDto = new ReserveInfoDto();

        try {
            log.info(String.format(" ==== applySeat() start  : %s ====",memberId));
            // 1. 등록된 사용자인지 확인
            Member member = memberRepository.findMember(memberId).orElseThrow(() -> new UserNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

            // 2. 자리 임시 배정 처리 ( RESERVED )
            Seat seat = seatRepository.findSeat(seatId).orElseThrow(() -> new OccupiedSeatException(ErrorCode.OCCUPIED_SEAT));

            seat.setStatus(SeatEnum.RESERVED.getStatus());
            seatRepository.save(seat);

            // 3. 예약 정보 저장
            Reservation reservation = new Reservation();
            reservation.setSeat(seat);
            reservation.setMember(member);
            reservation.setConfirmYn("N");
//          unique key로 동시성 제어할때 필요
//            reservation.setStatus(SeatEnum.RESERVED.getStatus());

            resultDto.setReservation(reservationRepository.save(reservation));
            log.info(String.format(" ==== applySeat() success  : %s ====",memberId));

        } catch (OccupiedSeatException e) { // 체크 당시 이미 자리가 차있을 경우
            log.error("=== Seat already occupied ===");
            resultDto.setResult(false);
            resultDto.setMessage(ErrorCode.OCCUPIED_SEAT.getMessage());
        } catch (DataIntegrityViolationException e) { // 체크한 이후에 들어갔을 경우 대비
            log.error(String.format("=== Seat occupied recently : %s ===", memberId));
            resultDto.setResult(false);
            resultDto.setMessage(ErrorCode.OCCUPIED_SEAT.getMessage());
        }catch(ObjectOptimisticLockingFailureException e){
            log.error(String.format("낙관적 락 exception 정상 발생 %s",e));
            resultDto.setResult(false);
            resultDto.setMessage(ErrorCode.OCCUPIED_SEAT.getMessage());
        } catch (Exception e) {
            log.error(e.toString());
            resultDto.setResult(false);
            resultDto.setMessage(e.toString());
        }

        return resultDto;
    }

}
