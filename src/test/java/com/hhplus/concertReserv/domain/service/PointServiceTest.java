package com.hhplus.concertReserv.domain.service;

import com.hhplus.concertReserv.domain.member.repositories.MemberRepository;
import com.hhplus.concertReserv.domain.reservation.repositories.PaymentRepository;
import com.hhplus.concertReserv.domain.token.repositories.TokenRepository;
import com.hhplus.concertReserv.domain.member.service.PointService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PointServiceTest {
    @InjectMocks
    private PointService pointService;
    @Mock
    private MemberRepository memberRepository;
    @DisplayName("충전금액이 음수인 경우 exception 처리")
    @Test
    void charge() {
        //given
        Long amount = -1L;
        UUID memberId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");

        //then
        assertThat(pointService.charge(memberId, amount).getResult()).isEqualTo(false);
    }

    @DisplayName("결제금액이 음수인 경우 exception 처리")
    @Test
    void paid() {

        Long amount = -1L;
        UUID memberId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");

        //then
        assertThat(pointService.paid(memberId, amount).getResult()).isEqualTo(false);
    }

    /**
     * 등록되지 않은 사용자일 경우 exception 처리
     */
    @Test
    void getPoint() {
        Long amount = -1L;
        UUID memberId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");

        //when
        when(memberRepository.findMember(memberId)).thenReturn(Optional.empty());
        //then
        assertThat(pointService.getPoint(memberId).getResult()).isEqualTo(false);
    }
}