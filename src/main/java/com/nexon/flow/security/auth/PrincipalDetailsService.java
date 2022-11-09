package com.nexon.flow.security.auth;

import com.nexon.flow.domain.entity.Member;
import com.nexon.flow.domain.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    final private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member principal = memberRepository.findByUsername(username)
                            .orElseThrow(()->{
                                return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다." + username);
                            });

        return new PrincipalDetails(principal);
    }

}
