package com.nexon.flow.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nexon.flow.domain.dto.Status;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member")
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"memberRoles"})
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //프로젝트에서 연결된 DB의 넘버링 전략을 사용한다.
    @Column(name = "member_id")
    private Long id;

    @Comment("이름")
    @Column(name = "username")
    private String username;

    @Comment("패스워드")
    @Column(name = "password")

    private String password;
    @Comment("DisplayName")
    @Column(name = "displayname")
    private String displayname;

    @Comment("이메일")
    @Column(name ="email")
    private String email;

//    @Comment("Directory")
//    @Column(name ="directory")
//    private String directory;

    @Comment("사용여부")
    @Column(name ="status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Comment("사번")
    @Column(name ="emp_no")
    private String EMPNO;

    @Comment("법인")
    @Column(name = "company_name")
    private String CMPName;

    @Comment("부서")
    @Column(name = "department_name")
    private String  DEPTName;

    @Comment("사용자 권한 매칭")
    @JsonManagedReference
    @ManyToMany(fetch = FetchType.EAGER,cascade={CascadeType.ALL})
    @JoinTable(name = "member_roles", joinColumns = { @JoinColumn(name = "member_id") },
                                      inverseJoinColumns = { @JoinColumn(name = "role_id") })
    private Set<Role> memberRoles = new HashSet<>();
}