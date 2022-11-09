package com.nexon.flow.domain.repository.member;

import com.nexon.flow.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>,
                                           QuerydslPredicateExecutor<Member>, MemberRepositoryCustom {

    Optional<Member> findByUsername(String username);
    Optional<Member> findByEmail(String email);

}
