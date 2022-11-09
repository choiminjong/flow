package com.nexon.flow.domain.dto.admin;

import com.nexon.flow.domain.dto.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDto {

    private Long id;
    private String email;
    private String username;
    private String password;
    private String displayname;
    private String directory;
    private Status status;
    private List<String> roles;
}