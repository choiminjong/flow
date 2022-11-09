package com.nexon.flow.domain.repository.resources;

import com.nexon.flow.domain.dto.admin.ResourceByRoleInterface;
import com.nexon.flow.domain.dto.admin.RoleMemberInterface;
import com.nexon.flow.domain.entity.Resources;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface ResourcesRepository extends JpaRepository<Resources, Long>,
                                             QuerydslPredicateExecutor<Resources>, ResourcesRepositoryCustom {

    Optional<Resources> findByResourceName(String resourceName);

    @Query("select r from Resources r join fetch r.roleSet where r.resourceType = 'url' ")
    List<Resources> findAllResources();

    @Query(value = "SELECT  RESOURCES.RESOURCE_ID ,RESOURCES.RESOURCE_NAME,ROLE.ROLE_NAME,ROLE.ROLE_DESC \n" +
                    "FROM RESOURCES INNER JOIN ROLE_RESOURCES   ON RESOURCES.RESOURCE_ID=ROLE_RESOURCES.RESOURCE_ID  \n" +
                    "INNER JOIN ROLE  ON ROLE.ROLE_ID=ROLE_RESOURCES.ROLE_ID  WHERE RESOURCES.RESOURCE_ID= ?1 \n" +
                    "ORDER BY RESOURCES.RESOURCE_ID ASC  ",
            countQuery = "SELECT count(*) \n" +
                    "FROM RESOURCES INNER JOIN ROLE_RESOURCES   ON RESOURCES.RESOURCE_ID=ROLE_RESOURCES.RESOURCE_ID  \n" +
                    "INNER JOIN ROLE  ON ROLE.ROLE_ID=ROLE_RESOURCES.ROLE_ID  WHERE RESOURCES.RESOURCE_ID= ?1  ",
            nativeQuery = true)
    Page<ResourceByRoleInterface> findResourceByRole(Long id, Pageable pageable);

}
