package com.nexon.flow.domain.repository.resources;

import com.nexon.flow.domain.dto.condition.ResourcesSearchCondition;
import com.nexon.flow.domain.entity.QResources;
import com.nexon.flow.domain.entity.Resources;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import com.querydsl.core.types.dsl.BooleanExpression;
import javax.persistence.EntityManager;
import java.util.List;

import static org.aspectj.util.LangUtil.isEmpty;

public class ResourcesRepositoryCustomImpl implements ResourcesRepositoryCustom {

    final private JPAQueryFactory queryFactory;

    public ResourcesRepositoryCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    QResources resources = QResources.resources;

    private BooleanExpression resourceNameEq(String resourceName) {
        return isEmpty(resourceName) ? null : resources.resourceName.contains(resourceName);
    }

    @Override
    public Page<Resources> getResourcesPage(ResourcesSearchCondition condition, Pageable pageable) {
            QueryResults<Resources> results = queryFactory
                                            .selectDistinct(resources)
                                            .from(resources)
                                            .where(
                                                    resourceNameEq(condition.getResourceName())
                                            )
                                            .offset(pageable.getOffset())
                                            .limit(pageable.getPageSize())
                                            .fetchResults();

            List<Resources> content = results.getResults();
            long total = results.getTotal();

            return new PageImpl<>(content, pageable, total);
    }

}
