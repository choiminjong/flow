package com.nexon.flow.domain.repository.member;

import com.nexon.flow.domain.dto.condition.MemberSearchCondition;
import com.nexon.flow.domain.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberRepositoryCustom {
    Page<Member> getMemberPage(MemberSearchCondition condition, Pageable pageable);
}
