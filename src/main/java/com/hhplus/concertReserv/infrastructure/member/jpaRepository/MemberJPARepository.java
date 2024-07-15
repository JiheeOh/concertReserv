package com.hhplus.concertReserv.infrastructure.member.jpaRepository;

import com.hhplus.concertReserv.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MemberJPARepository extends JpaRepository<Member, UUID> {
}
