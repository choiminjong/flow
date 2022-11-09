package com.nexon.flow.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "resources")
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"roleSet"})
public class Resources extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "resource_id")
    private Long id;

    @Comment("URL")
    @Column(name = "resource_name")
    private String resourceName;

    @Comment("http method")
    @Column(name = "http_method")
    private String httpMethod;

    @Comment("resource")
    @Column(name = "resource_type")
    private String resourceType;

    @Comment("URL 권한 매칭")
    @JsonManagedReference
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "role_resources", joinColumns = {@JoinColumn(name = "resource_id") },
                                        inverseJoinColumns = { @JoinColumn(name = "role_id") })
    private Set<Role> roleSet = new HashSet<>();

//    public List<String> getRoles() {
//        return roleSet.stream().map(Role::getRoleName).collect(Collectors.toList());
//    }
}
