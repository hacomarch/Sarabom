package com.example.sarabom.domain.member.infrastructure;

import com.example.sarabom.domain.member.domain.Member;
import com.example.sarabom.domain.member.domain.MemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByEmailAndStatus(String email, MemberStatus status);

    Optional<Member> findByEmailAndStatus(String email, MemberStatus status);
}
