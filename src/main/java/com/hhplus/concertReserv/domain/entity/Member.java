package com.hhplus.concertReserv.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Member extends BaseEntity {

    @Id
    private UUID memberId;

    private String memberNm;

    private Long point;

}
