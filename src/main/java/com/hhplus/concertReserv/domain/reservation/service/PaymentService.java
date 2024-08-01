package com.hhplus.concertReserv.domain.reservation.service;

import com.hhplus.concertReserv.domain.concert.dto.ConcertDto;
import com.hhplus.concertReserv.domain.concert.dto.SeatDto;
import com.hhplus.concertReserv.domain.reservation.dto.PaymentDto;
import com.hhplus.concertReserv.domain.reservation.dto.ReserveDto;
import com.hhplus.concertReserv.domain.reservation.dto.ReserveInfoDto;
import com.hhplus.concertReserv.domain.reservation.entity.Payment;
import com.hhplus.concertReserv.domain.reservation.repositories.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository){
        this.paymentRepository = paymentRepository;
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
}
