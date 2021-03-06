package com.hongikbros.jobmanager.member.domain;

import com.hongikbros.jobmanager.common.domain.BaseEntity;

import javax.persistence.*;

@Entity
@Table(indexes = @Index(name = "oauth_id", columnList = "oauthId"))
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private Long oauthId;

    private String name;

    private String email;

    private String avatar;

    @Enumerated(EnumType.STRING)
    private Role role;

    protected Member() {
    }

    private Member(Long id, Long oauthId, String name, String email, String avatar,
            Role role) {
        this.id = id;
        this.oauthId = oauthId;
        this.name = name;
        this.email = email;
        this.avatar = avatar;
        this.role = role;
    }

    public static Member of(Long oauthId, String name, String email, String avatar,
            String loginId) {
        return new Member(null, oauthId, name, email, avatar, RoleFactory.creatFrom(loginId));
    }

    public Member update(String name, String avatar) {
        this.name = name;
        this.avatar = avatar;
        return this;
    }

    public Long getId() {
        return id;
    }

    public Long getOauthId() {
        return oauthId;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }
}
