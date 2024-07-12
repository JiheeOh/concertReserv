package com.hhplus.concertReserv.infrastructure.Impl;

import com.hhplus.concertReserv.domain.entity.Member;
import com.hhplus.concertReserv.domain.repository.MemberRepository;
import com.hhplus.concertReserv.infrastructure.jpaRepository.MemberJPARepository;
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
    public Optional<Member> findMember(UUID memberId) {
        return memberJPARepo.findById(memberId);
    }

    @Override
    public Member save(Member member) {
        return memberJPARepo.save(member);
    }
}
