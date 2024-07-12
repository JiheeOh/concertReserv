package com.hhplus.concertReserv.domain.repository;

import com.hhplus.concertReserv.domain.dto.PointDto;
import com.hhplus.concertReserv.domain.entity.Member;

import java.util.Optional;
import java.util.UUID;

public interface MemberRepository {

    Optional<Member> findMember(UUID memberId);

    Member save(Member member);

}
