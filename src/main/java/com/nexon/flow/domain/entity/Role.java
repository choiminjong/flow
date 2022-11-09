package com.nexon.flow.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "role")
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"members","resourcesSet"})
public class Role extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Comment("권한명")
    @Column(name = "role_name")
    private String roleName;

    @Comment("설명")
    @Column(name = "role_desc")
    private String roleDesc;

    @Comment("권한 사용자 매칭")
    @JsonBackReference
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "memberRoles")
    private Set<Member> members = new HashSet<>();

//    @JsonProperty("members")
//    public List<String> getMembers() {
//        return members.stream().map(Member::getUsername).collect(Collectors.toList());
//    }

    @Comment("URL 권한 매칭")
    @JsonBackReference
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roleSet")
    private Set<Resources> resourcesSet = new HashSet<>();

}