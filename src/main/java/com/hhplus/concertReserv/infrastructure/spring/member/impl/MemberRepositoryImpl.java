package com.hhplus.concertReserv.infrastructure.spring.member.impl;

import com.hhplus.concertReserv.domain.member.entity.Users;
import com.hhplus.concertReserv.domain.member.repositories.MemberRepository;
import com.hhplus.concertReserv.infrastructure.spring.member.jpaRepository.MemberJPARepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class MemberRepositoryImpl implements MemberRepository {
    private final MemberJPARepository memberJPARepo;

    public MemberRepositoryImpl(MemberJPARepository memberJPARepo) {
        this.memberJPARepo = memberJPARepo;
    }

    @Override
    public Optional<Users> findMember(UUID userId) {
        return memberJPARepo.findById(userId);
    }

    @Override
    public Users save(Users member) {
        return memberJPARepo.save(member);
    }
}
