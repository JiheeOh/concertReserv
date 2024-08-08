package com.hhplus.concertReserv.infrastructure.member.impl;

import com.hhplus.concertReserv.domain.member.entity.Users;
import com.hhplus.concertReserv.domain.member.repositories.MemberRepository;
import com.hhplus.concertReserv.infrastructure.member.jpaRepository.MemberJPARepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class MemberRepositoryImpl implements MemberRepository {
    private final MemberJPARepository memberJPARepo;

    public MemberRepositoryImpl(MemberJPARepository memberJPARepo){
        this.memberJPARepo = memberJPARepo;
    }

    @Override
    public Optional<Users> findMember(UUID memberId) {
        return memberJPARepo.findById(memberId);
    }

    @Override
    public Users save(Users member) {
        return memberJPARepo.save(member);
    }
}
