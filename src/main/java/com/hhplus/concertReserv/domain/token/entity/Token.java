package com.hhplus.concertReserv.domain.token.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.UUID;

@RedisHash("Token")
@Getter
@Setter
//@SequenceGenerator(name = "TOKEN_SEQ_GENERATOR",
//        sequenceName = "TOKEN_SEQ",
//        initialValue = 1,
//        allocationSize = 1
//)
public class Token implements Serializable {

//    @GeneratedValue(strategy = GenerationType.SEQUENCE,
//                    generator = "TOKEN_SEQ_GENERATOR")

    @Id
    private UUID memberId;

    private UUID concertId;

    private double createDt;


    public Token(UUID memberId, UUID concertId) {
        this.memberId = memberId;
        this.concertId = concertId;
    }


    @Override
    public String toString() {
        return
                "memberId=" + memberId +
                        ", concertId=" + concertId;

    }
}
