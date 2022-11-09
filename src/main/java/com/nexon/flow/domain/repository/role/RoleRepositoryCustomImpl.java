package com.nexon.flow.domain.repository.role;

import com.nexon.flow.domain.dto.condition.RoleSearchCondition;
import com.nexon.flow.domain.entity.QMember;
import com.nexon.flow.domain.entity.QRole;
import com.nexon.flow.domain.entity.Role;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

import static org.aspectj.util.LangUtil.isEmpty;

public class RoleRepositoryCustomImpl implements RoleRepositoryCustom{

    final private JPAQueryFactory queryFactory;

    public RoleRepositoryCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    QRole role = QRole.role;
    QMember member = QMember.member;

    private BooleanExpression rolenameEq(String rolename) {
        return isEmpty(rolename) ? null : role.roleName.contains(rolename);
    }

    @Override
    public Page<Role> getRolePage(RoleSearchCondition condition, Pageable pageable) {

        QueryResults<Role> results = queryFactory
                                    .selectDistinct(role)
                                    .from(role)
                                    .where(
                                            rolenameEq(condition.getRoleName())
                                    )
                                    .offset(pageable.getOffset())
                                    .limit(pageable.getPageSize())
                                    .fetchResults();

        List<Role> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }
}
