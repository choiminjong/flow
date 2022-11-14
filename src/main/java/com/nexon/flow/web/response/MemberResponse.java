package com.nexon.flow.web.response;

import com.nexon.flow.domain.dto.Status;
import com.nexon.flow.domain.entity.Member;
import com.nexon.flow.domain.entity.Role;
import org.springframework.data.domain.Page;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberResponse {

    private Long id;
    private String email;
    private String username;
    private String displayname;
    private String directory;
    private Date moddt;
    private Date regdt;
    private Status status;
    private List<String> roles;

    @Data
    @Builder
    public static class MembertoDtoList {
        private Long id;
        private String email;
        private String username;
        private String displayname;
        private String directory;
        private Date regdt;
        private Date moddt;
        private Status status;
        private Set<Role> roles;
    }

    /*
      Page<Member> -> Page<MemberDto> Entity 변경
    */
    public Page<MemberResponse.MembertoDtoList> toDtoList(Page<Member> memberList){
        Page<MemberResponse.MembertoDtoList> memberDtoList = memberList.map(m -> MemberResponse.MembertoDtoList.builder()
                                                            .id(m.getId())
                                                            .email(m.getEmail())
                                                            .username(m.getUsername())
                                                            .displayname(m.getDisplayname())
                                                            //.directory(m.getDirectory())
                                                            .regdt(m.getRegdt())
                                                            .moddt(m.getModdt())
                                                            .status(m.getStatus())
                                                            .roles(m.getMemberRoles())
                                                            .build());
        return memberDtoList;
    }
}
