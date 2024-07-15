package com.hhplus.concertReserv.domain.service;

import com.hhplus.concertReserv.domain.concert.entity.Concert;
import com.hhplus.concertReserv.domain.concert.entity.ConcertSchedule;
import com.hhplus.concertReserv.domain.concert.entity.Seat;
import com.hhplus.concertReserv.domain.concert.entity.SeatPK;
import com.hhplus.concertReserv.domain.concert.repositories.SeatRepository;
import com.hhplus.concertReserv.domain.member.repositories.MemberRepository;
import com.hhplus.concertReserv.domain.reservation.repositories.PaymentRepository;
import com.hhplus.concertReserv.domain.reservation.repositories.ReservationRepository;
import com.hhplus.concertReserv.domain.token.repositories.TokenRepository;
import com.hhplus.concertReserv.domain.reservation.service.ReservationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {
    @InjectMocks
    private ReservationService reservationService;
    @Mock
    private SeatRepository seatRepository;
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private TokenRepository tokenRepository;

    /**
     * 등록된 콘서트 Id로 예약 가능한 날짜와 자리 조회
     * - DTO을 원하는 방향대로 만들어서 반환하는지 확인
     */
    @Test
    void findReserveAvailable() {
        //given
        UUID concertID = UUID.fromString("e50b778-4de9-40e1-b9ba-9b1e786b4197");
        List<Seat> result = new ArrayList<>();
        SeatPK seatPk = new SeatPK(UUID.fromString("280a8a4d-a27f-4d01-b031-2a003cc4c039"), "AVAILABLE");

        Concert concert = new Concert();
        concert.setConcertId(concertID);
        concert.setPerformer("한국교향악단");
        concert.setConcertNm("연말콘서트");

        ConcertSchedule concertSchedule = new ConcertSchedule();
        concertSchedule.setConcertDt(LocalDateTime.of(2024, 12, 25, 13, 0, 0));
        concertSchedule.setConcertId(concert);
        concertSchedule.setHallNm("메인홀 A");

        Seat seat = new Seat();
        seat.setSeatPk(seatPk);
        seat.setConcertSchedule(concertSchedule);
        seat.setSeatNo(1L);
        seat.setSeatClass("VVIP");
        seat.setPrice(200000L);

        result.add(seat);

        //when
        when(seatRepository.findReserveAvailable(concertID)).thenReturn(result);

        //Then
        assertThat(reservationService.findReserveAvailable(concertID).getConcert().getConcertId()).isEqualTo(result.get(0).getConcertSchedule().getConcertId().getConcertId());
        assertThat(reservationService.findReserveAvailable(concertID).getConcert().getConcertDate()).isEqualTo(result.get(0).getConcertSchedule().getConcertDt());
        assertThat(reservationService.findReserveAvailable(concertID).getConcert().getConcertNm()).isEqualTo(result.get(0).getConcertSchedule().getConcertId().getConcertNm());
        assertThat(reservationService.findReserveAvailable(concertID).getConcert().getSeat().get(0).getSeatNo()).isEqualTo(result.get(0).getSeatNo());
        assertThat(reservationService.findReserveAvailable(concertID).getConcert().getSeat().get(0).getSeatClass()).isEqualTo(result.get(0).getSeatClass());
        assertThat(reservationService.findReserveAvailable(concertID).getConcert().getSeat().get(0).getPrice()).isEqualTo(result.get(0).getPrice());

    }

    /**
     * 없는 좌석 ID 사용 시
     * OccupiedSeatException 발생
     */
    @DisplayName("없는 좌석 ID 사용시 fail")
    @Test
    void applySeat_with_INVALID_SEAT() {
        //given
        UUID memberId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        UUID concertID = UUID.fromString("e50b778-4de9-40e1-b9ba-9b1e786b4197");
        UUID seatId = UUID.fromString("280a8a4d-a27f-4d01-b031-2a003cc4c039");
        //when
        when(seatRepository.findSeat(seatId)).thenReturn(Optional.empty());

        //then
        assertThat(reservationService.applySeat(memberId, concertID).getResult()).isEqualTo(false);

    }


    /**
     * 없는 유저 ID 사용 시
     * UserNotFoundException 발생
     */
    @DisplayName("없는 유저 ID 사용시 fail")
    @Test
    void applySeat_with_INVALID_MEMBER() {
        //given
        UUID memberId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        UUID concertID = UUID.fromString("e50b778-4de9-40e1-b9ba-9b1e786b4197");
        UUID seatId = UUID.fromString("280a8a4d-a27f-4d01-b031-2a003cc4c039");
        //when
        when(seatRepository.findSeat(seatId)).thenReturn(Optional.of(new Seat()));
        when(memberRepository.findMember(memberId)).thenReturn(Optional.empty());
        //then
        assertThat(reservationService.applySeat(memberId, concertID).getResult()).isEqualTo(false);

    }
}