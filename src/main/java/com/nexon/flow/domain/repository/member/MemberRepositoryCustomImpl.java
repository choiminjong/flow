package com.nexon.flow.domain.repository.member;

import com.nexon.flow.domain.dto.condition.MemberSearchCondition;
import com.nexon.flow.domain.entity.Member;
import com.nexon.flow.domain.entity.QMember;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;
import static org.aspectj.util.LangUtil.isEmpty;

public class MemberRepositoryCustomImpl implements MemberRepositoryCustom{

    final private JPAQueryFactory queryFactory;

    public MemberRepositoryCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    QMember member = QMember.member;

    private BooleanExpression usernameEq(String username) {
        return isEmpty(username) ? null : member.username.contains(username);
    }

    /*
        selectDistinct 중복 데이터 제거
     */
    @Override
    public Page<Member> getMemberPage(MemberSearchCondition condition, Pageable pageable) {

        QueryResults<Member> results = queryFactory
                                    .selectDistinct(member)
                                    .from(member)
                                    .innerJoin(member.memberRoles)
                                    .where(
                                        usernameEq(condition.getUsername())
                                    )
                                    .offset(pageable.getOffset())
                                    .limit(pageable.getPageSize())
                                    .fetchResults();

        List<Member> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }
}
