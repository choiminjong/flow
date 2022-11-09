package com.nexon.flow.service.admin;

import com.nexon.flow.core.except.ErrorCode;
import com.nexon.flow.core.except.Exception;
import com.nexon.flow.domain.dto.admin.ResourcesDto;
import com.nexon.flow.domain.dto.admin.RoleDto;
import com.nexon.flow.domain.dto.admin.RoleMemberInterface;
import com.nexon.flow.domain.dto.condition.RoleSearchCondition;
import com.nexon.flow.domain.entity.Role;
import com.nexon.flow.domain.repository.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RolerService {

    final private RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public Role getRoleSearch(String rolename) {
        return roleRepository.findByRoleName(rolename).orElse(new Role());
    }

    @Transactional(readOnly = true)
    public Page<Role> getRolePage(String searchText, Pageable pageable) {
        RoleSearchCondition condition = new RoleSearchCondition();
        condition.setRoleName(searchText);
        return roleRepository.getRolePage(condition,pageable);
    }

    @Transactional(readOnly = true)
    public Page<RoleMemberInterface> findByRoleByMember(String rolename, Pageable pageable) {
        return roleRepository.findByRoleByMember(rolename,pageable);
    }

    @Transactional
    public void modifyRole(Long id ,Role role){
        role.setId(id);
        roleRepository.save(role);
    }


    @Transactional(readOnly = true)
    public Role getByRoleName(String rolename) {
        return roleRepository.findByRoleName(rolename).orElseThrow(() -> new Exception(ErrorCode.NOT_FOUND));
    }

    @Transactional
    public Role getById(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new Exception(ErrorCode.NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<Role> getRoleNameContaining(String searchText, Pageable pageable) {
        return roleRepository.findByRoleNameContaining(searchText,pageable);
    }

    @Transactional
    public Role save(Role role){
        validateDuplicateRole(role);
        return roleRepository.save(role);
    }

    @Transactional(readOnly = true)
    public void validateDuplicateRole(Role role){
        if(roleRepository.findByRoleName(role.getRoleName()).isPresent()){
            throw new Exception(ErrorCode.DUPLICATE_ROLE);
        }
    }

    @Transactional
    public void deleteById(Long id){
        roleRepository.deleteById(id);
    }
}
