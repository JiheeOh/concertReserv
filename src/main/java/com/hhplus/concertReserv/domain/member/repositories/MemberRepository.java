package com.hhplus.concertReserv.domain.member.repositories;

import com.hhplus.concertReserv.domain.member.entity.Member;

import java.util.Optional;
import java.util.UUID;

public interface MemberRepository {

    Optional<Member> findMember(UUID memberId);

    Member save(Member member);

}
