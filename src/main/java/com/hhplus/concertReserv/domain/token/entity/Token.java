package com.hhplus.concertReserv.domain.token.entity;

import com.hhplus.concertReserv.domain.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Getter
@Setter
@SequenceGenerator(name = "TOKEN_SEQ_GENERATOR",
        sequenceName = "TOKEN_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class Token extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "TOKEN_SEQ_GENERATOR")
    private Long tokenId;

    private Long waitOrder;

    private int status;

    private UUID memberId;

    private UUID concertId;

    private LocalDateTime activateDt;


}
