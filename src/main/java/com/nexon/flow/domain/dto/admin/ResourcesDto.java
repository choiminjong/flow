package com.nexon.flow.domain.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResourcesDto {

    private Long id;
    private String resourceName;
    private String httpMethod;
    private String resourceType;
    private Date regdt;
    private Date moddt;
    private List<String> roles;
}
