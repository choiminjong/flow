package com.nexon.flow.web.response;

import com.nexon.flow.domain.entity.Resources;
import org.springframework.data.domain.Page;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResourcesResponse {

    private Long id;
    private String resourceName;
    private String httpMethod;
    private String resourceType;
    private Date regdt;
    private Date moddt;
    private List<String> roles;
    //private Set<Role> roleSet;

    @Data
    @Builder
    public static class ResourcestoDtoList {
        private Long id;
        private String resourceName;
        private String httpMethod;
        private String resourceType;
        private Date regdt;
        private Date moddt;
    }

    /*
    Page<Resources> -> Page<ResourcesDto> Entity 변경
    */
    public Page<ResourcesResponse.ResourcestoDtoList> toDtoList(Page<Resources> resourcesList){
        Page<ResourcestoDtoList> resourcesDtoList = resourcesList.map(m -> ResourcesResponse.ResourcestoDtoList.builder()
                                                    .id(m.getId())
                                                    .resourceName(m.getResourceName())
                                                    .httpMethod(m.getHttpMethod())
                                                    .resourceType(m.getResourceType())
                                                    .regdt(m.getRegdt())
                                                    .moddt(m.getModdt())
                                                    .build());

        return resourcesDtoList;
    }
}
