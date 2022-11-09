package com.nexon.flow.web.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
public class RoleRequest {

    @Data
    public static class Create {
        private String roleName;
        private String roleDesc;
    }

    @Data
    public static class ModifiyRole {
        //private Long id;
        private String roleName;
        private String roleDesc;
    }

}
