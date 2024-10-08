package com.hhplus.concertReserv.infrastructure.spring.member.jpaRepository;

import com.hhplus.concertReserv.domain.member.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MemberJPARepository extends JpaRepository<Users, UUID> {

}
