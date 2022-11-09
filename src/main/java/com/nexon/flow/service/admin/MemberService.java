package com.nexon.flow.service.admin;

import com.nexon.flow.core.except.ErrorCode;
import com.nexon.flow.core.except.Exception;
import com.nexon.flow.domain.dto.Status;
import com.nexon.flow.domain.dto.admin.MemberDto;
import com.nexon.flow.domain.dto.condition.MemberSearchCondition;
import com.nexon.flow.domain.entity.Member;
import com.nexon.flow.domain.entity.Role;
import com.nexon.flow.domain.repository.member.MemberRepository;
import com.nexon.flow.web.response.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    final private MemberRepository memberRepository;
    final private RolerService rolerService;
    final private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional(readOnly = true)
    public Member getByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(() -> new Exception(ErrorCode.NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public Page<Member> getMemberPage(String searchText, Pageable pageable) {

        MemberSearchCondition condition = new MemberSearchCondition();
        condition.setUsername(searchText);
        return memberRepository.getMemberPage(condition,pageable);
    }

    @Transactional
    public void ModifyMemberPassword(Long id , String chagePassword) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new Exception(ErrorCode.NOT_FOUND));
        member.setPassword(bCryptPasswordEncoder.encode(chagePassword));
        memberRepository.save(member);
    }

    @Transactional
    public void modifyMember(MemberDto memberDto){

        Member member = memberRepository.findById(memberDto.getId()).orElseThrow(() -> new Exception(ErrorCode.NOT_FOUND));
        member.setEmail(memberDto.getEmail());
        member.setDisplayname(memberDto.getDisplayname());
        member.setUsername(memberDto.getUsername());
        member.setStatus(memberDto.getStatus());
        member.setDirectory(memberDto.getDirectory());

//        Set<Role> roles = new HashSet<>();
//        if(memberDto.getRoles() != null){
//            memberDto.getRoles().forEach(role -> {
//                                Role r = rolerService.getByRoleName(role);
//                                roles.add(r);
//            });
//            member.setMemberRoles(roles);
//        }else{
//            member.setMemberRoles(roles);
//        }

        memberRepository.save(member);
    }

    @Transactional
    public MemberResponse getById(Long id) {

        Member member = memberRepository.findById(id).orElseThrow(() -> new Exception(ErrorCode.NOT_FOUND));
        ModelMapper modelMapper = new ModelMapper();
        MemberResponse memberResponse = modelMapper.map(member, MemberResponse.class);

        List<String> roles = member.getMemberRoles()
                             .stream()
                             .map(role -> role.getRoleName())
                             .collect(Collectors.toList());

        memberResponse.setRoles(roles);
        return memberResponse;
    }

    @Transactional(readOnly = true)
    public Member getByUsername(String usernmae) {
        return memberRepository.findByUsername(usernmae).orElseThrow(() -> new Exception(ErrorCode.NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public Member getMemberSearch(String usernmae) {
        return memberRepository.findByUsername(usernmae).orElse(new Member());
    }

    @Transactional(readOnly = true)
    public List<Member> getMembers() {
        return memberRepository.findAll();
    }


    @Transactional
    public Member save(Member Member){

        validateDuplicateMember(Member);
        Member.setPassword((bCryptPasswordEncoder.encode(Member.getPassword())));
        Member.setStatus(Status.Activate);

        Role role = rolerService.getRoleSearch("ROLE_USER");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        Member.setMemberRoles(roles);

        return memberRepository.save(Member);
    }

    @Transactional(readOnly = true)
    public void validateDuplicateMember(Member Member){

        if(memberRepository.findByEmail(Member.getEmail()).isPresent()){
            throw new Exception(ErrorCode.DUPLICATE_USER);
        }

        if(memberRepository.findByUsername(Member.getUsername()).isPresent()){
            throw new Exception(ErrorCode.DUPLICATE_USER);
        }
    }
}
