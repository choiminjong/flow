package com.nexon.flow.domain.repository.resources;

import com.nexon.flow.domain.dto.condition.ResourcesSearchCondition;
import com.nexon.flow.domain.entity.Resources;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ResourcesRepositoryCustom {
    Page<Resources> getResourcesPage(ResourcesSearchCondition condition, Pageable pageable);
}
