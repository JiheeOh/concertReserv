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
public class Concert extends BaseEntity{

    @Id
    @Column(name="CONCERT_ID")
    private UUID concertId;

    private String performer;

    private String concertNm;
}
