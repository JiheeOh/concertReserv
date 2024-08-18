package com.hhplus.concertReserv.domain.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // Custom Exception

    // 토큰이 비활성화된 상태
    TOKEN_DEACTIVATED(500,"500","Token is deactivated"),

    // 등록된 토큰 ID가 아닐 때
    TOKEN_NOT_FOUND(500,"500","Invalid tokenId"),

    // 등록된 사용자 ID가 아닐 때
    MEMBER_NOT_FOUND(500,"500","Invalid memberId"),

    // 등록된 콘서트 ID가 아닐 때
    CONCERT_NOT_FOUND(500,"500","Invalid concertId "),

    // 등록된 결제 ID가 아닐 때
    PAYMENT_NOT_FOUNT(500,"500","Invalid paymentId"),

    // 자리가 이미 점유되었을 때
    OCCUPIED_SEAT(500,"500","Seat already occupied"),

    // 충전값이 음수일 때
    INVALID_AMOUNT(500,"500","Amount can't be null"),

    // 포인트가 부족할 때
    NOT_ENOUGH_AMOUNT(500,"500","There's no enough point to pay"),

    // reservation 관련 outbox 정보 부재
    RESERVATION_OUTBOX_NOT_FOUND(500,"500","Can't find reservation Outbox "),

    // payment 관련 outbox 정보 부재
    PAYMENT_OUTBOX_NOT_FOUND(500,"500","Can't find payment Outbox"),

    // 모든 에러처리
    INTERNAL_SERVER_ERROR(500,"500","Internal Server Error");


    private final int status;
    private final String code;
    private final String message;

    ErrorCode(final int status, final String code, final String message){
        this.status = status;
        this.code = code;
        this.message = message;

    }
}
