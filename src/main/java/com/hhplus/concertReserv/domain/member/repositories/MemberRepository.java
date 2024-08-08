package com.hhplus.concertReserv.domain.member.repositories;

import com.hhplus.concertReserv.domain.member.entity.Users;

import java.util.Optional;
import java.util.UUID;

public interface MemberRepository {

    Optional<Users> findMember(UUID memberId);

    Users save(Users member);

}
