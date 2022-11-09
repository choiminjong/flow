package com.nexon.flow.security.service;

import com.nexon.flow.domain.entity.Resources;
import com.nexon.flow.domain.repository.resources.ResourcesRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class SecurityResourceService {

    private ResourcesRepository resourcesRepository;

    public SecurityResourceService(ResourcesRepository resourcesRepository) {
        this.resourcesRepository = resourcesRepository;
    }

    public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getResourceList() {

        LinkedHashMap<RequestMatcher, List<ConfigAttribute>> result = new LinkedHashMap<>();
        List<Resources> resourcesList = resourcesRepository.findAllResources();

        resourcesList.forEach(re -> {
                List<ConfigAttribute> configAttributeList = new ArrayList<>();
                re.getRoleSet().forEach(ro -> {
                    configAttributeList.add(new SecurityConfig(ro.getRoleName()));
                    result.put(new AntPathRequestMatcher(re.getResourceName()), configAttributeList);
                });
            }
        );

        return result;
    }
}