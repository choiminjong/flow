package com.nexon.flow.core.init;

import com.nexon.flow.domain.dto.Status;
import com.nexon.flow.domain.entity.Member;
import com.nexon.flow.domain.entity.Role;
import com.nexon.flow.service.admin.MemberService;
import com.nexon.flow.service.admin.RolerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class InitService implements InitializingBean {

    final private RolerService rolerService;
    final private MemberService memberService;
    final private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void afterPropertiesSet(){

        System.out.println("서비스 [ROLE_USER/ROLE_ADMIN] 권한 확인");

        Role roleUser= rolerService.getRoleSearch("ROLE_USER");
        if(roleUser.getRoleName() == null) {
            System.out.println("서비스 [ROLE_USER] 권한 생성");
            Role user = Role.builder().roleDesc("ROLE_USER").roleName("ROLE_USER").build();
            rolerService.save(user);
         }

        Role roleAdmin= rolerService.getRoleSearch("ROLE_ADMIN");
        if(roleAdmin.getRoleName() == null) {
            System.out.println("서비스 [ROLE_ADMIN] 권한 생성");
            Role admin = Role.builder().roleDesc("ROLE_ADMIN").roleName("ROLE_ADMIN").build();
            rolerService.save(admin);
        }

        System.out.println("관리자 계정 확인");

        Member originUser = memberService.getByUsername("admin");
        if(originUser.getUsername() == null){
            System.out.println("관리자 계정 생성");

            Member member = new Member();
            member.setPassword((bCryptPasswordEncoder.encode("1234")));
            member.setStatus(Status.Activate);

            Role role = rolerService.getRoleSearch("ROLE_ADMIN");
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            member.setMemberRoles(roles);
            memberService.save(member);
        }

    }
}

