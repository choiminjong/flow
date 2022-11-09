package com.nexon.flow.domain.repository.role;

import com.nexon.flow.domain.dto.condition.RoleSearchCondition;
import com.nexon.flow.domain.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleRepositoryCustom {

    Page<Role> getRolePage(RoleSearchCondition condition, Pageable pageable);
}

