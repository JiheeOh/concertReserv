package com.hhplus.concertReserv.domain.member.entity;

import com.hhplus.concertReserv.domain.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
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

    @Version
    private Long version;

}
