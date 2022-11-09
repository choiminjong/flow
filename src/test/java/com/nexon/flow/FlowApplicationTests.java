package com.nexon.flow;
import com.nexon.flow.domain.entity.QMember;
import com.nexon.flow.domain.entity.QRole;
import com.nexon.flow.domain.repository.role.RoleRepository;
import com.nexon.flow.service.admin.RolerService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;


@SpringBootTest
@Transactional
class FlowApplicationTests {

    @PersistenceContext
    EntityManager em;

    @Autowired
    JPAQueryFactory queryFactory;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RolerService rolerService;

    QMember member = QMember.member;
    QRole role = QRole.role;


    @Test
    void contextLoads() {

    }




}

