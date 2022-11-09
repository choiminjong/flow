package com.nexon.flow.domain.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleDto {

    private String id;
    private String roleName;
    private String roleDesc;
    private int memberSize;
    private ArrayList<HashMap<String, String>> memberList ;

    @Data
    public static class RoleMember {
        private String username;
    }
}





