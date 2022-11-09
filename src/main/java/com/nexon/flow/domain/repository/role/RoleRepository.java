package com.nexon.flow.domain.repository.role;

import com.nexon.flow.domain.dto.admin.RoleMemberInterface;
import com.nexon.flow.domain.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long>,
                                        QuerydslPredicateExecutor<Role>, RoleRepositoryCustom {

    Optional<Role> findByRoleName(String rolename);
    Page<Role> findByRoleNameContaining(String rolename, Pageable pageable);

    @Query(value = "SELECT  MEMBER.MEMBER_ID,MEMBER.USERNAME, ROLE.ROLE_ID, ROLE.ROLE_NAME , ROLE.ROLE_DESC \n" +
                   "FROM MEMBER  INNER JOIN MEMBER_ROLES  ON MEMBER.MEMBER_ID=MEMBER_ROLES.MEMBER_ID \n" +
                   "INNER JOIN ROLE ON ROLE.ROLE_ID=MEMBER_ROLES.ROLE_ID WHERE ROLE.ROLE_NAME=?1 \n" +
                   "ORDER BY MEMBER.MEMBER_ID ASC  ",
            countQuery = "SELECT count(*) \n" +
                         "FROM MEMBER  INNER JOIN MEMBER_ROLES  ON MEMBER.MEMBER_ID=MEMBER_ROLES.MEMBER_ID \n" +
                         "INNER JOIN ROLE ON ROLE.ROLE_ID=MEMBER_ROLES.ROLE_ID WHERE ROLE.ROLE_NAME=?1  ",
            nativeQuery = true)
    Page<RoleMemberInterface> findByRoleByMember(String roleName, Pageable pageable);

    @Override
    void delete(Role role);
}