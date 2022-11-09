package com.nexon.flow.service.admin;

import com.nexon.flow.core.except.ErrorCode;
import com.nexon.flow.core.except.Exception;
import com.nexon.flow.domain.dto.admin.ResourceByRoleInterface;
import com.nexon.flow.domain.dto.admin.ResourcesDto;
import com.nexon.flow.domain.dto.condition.ResourcesSearchCondition;
import com.nexon.flow.domain.entity.Resources;
import com.nexon.flow.domain.entity.Role;
import com.nexon.flow.domain.repository.resources.ResourcesRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ResourcesService {

    final private ResourcesRepository resourcesRepository;
    final private RolerService rolerService;

    @Transactional(readOnly = true)
    public Page<Resources> getResourcesPage(String searchText, Pageable pageable) {
        ResourcesSearchCondition condition = new ResourcesSearchCondition();
        condition.setResourceName(searchText);
       return resourcesRepository.getResourcesPage(condition,pageable);
    }

    @Transactional(readOnly = true)
    public Page<ResourceByRoleInterface> findResourceByRole(Long id, Pageable pageable) {
        return resourcesRepository.findResourceByRole(id,pageable);
    }

    @Transactional
    public ResourcesDto getById(Long id) {

        Resources resources = resourcesRepository.findById(id).orElseThrow(() -> new Exception(ErrorCode.NOT_FOUND));
        ModelMapper modelMapper = new ModelMapper();
        ResourcesDto resourcesDto = modelMapper.map(resources, ResourcesDto.class);

        List<String> role = new ArrayList<>();
        if(resources.getRoleSet() != null) {
            resources.getRoleSet().forEach(ro -> {
                role.add(ro.getRoleName());
            });
        }

        resourcesDto.setRoles(role);
        return resourcesDto;
    }

    @Transactional
    public void modifyResources(Long id,ResourcesDto resourcesDto){

        Resources resources = resourcesRepository.findById(id).orElseThrow(() -> new Exception(ErrorCode.NOT_FOUND));

        resources.setResourceName(resourcesDto.getResourceName());
        resources.setHttpMethod(resourcesDto.getHttpMethod());
        resources.setResourceType(resourcesDto.getResourceType());

        Set<Role> roles = new HashSet<>();
        if(resourcesDto.getRoles() != null){
            resourcesDto.getRoles().forEach(role -> {
                    Role r = rolerService.getByRoleName(role);
                    roles.add(r);
            });
            resources.setRoleSet(roles);
        }else{
            resources.setRoleSet(roles);
        }

        resourcesRepository.save(resources);
    }

    @Transactional(readOnly = true)
    public Resources getByResourceName(String resourceName) {
        return resourcesRepository.findByResourceName(resourceName)
                                     .orElseThrow(() -> new Exception(ErrorCode.NOT_FOUND));
    }

    @Transactional
    public Resources save(Resources resources){
        validateDuplicateResources(resources);
        return resourcesRepository.save(resources);
    }

    @Transactional(readOnly = true)
    public void validateDuplicateResources(Resources resources){
        if(resourcesRepository.findByResourceName(resources.getResourceName()).isPresent()){
            throw new Exception(ErrorCode.DUPLICATE_RESOURCE);
        }
    }

    @Transactional
    public void deleteById(Long id){
        resourcesRepository.deleteById(id);
    }

}
