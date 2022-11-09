package com.nexon.flow.web.response;

import lombok.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleResponse {

    private String id;
    private String roleName;
    private String roleDesc;
    private List<String> memberList;
}
