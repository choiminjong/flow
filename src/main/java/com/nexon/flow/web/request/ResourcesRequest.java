package com.nexon.flow.web.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResourcesRequest {

    private String resourceName;
    private String httpMethod;
    private String resourceType;

    @Data
    public static class ModifiyResources {
        //private Long id;
        private String resourceName;
        private String httpMethod;
        private String resourceType;
        private List<String> roles;
    }

}
