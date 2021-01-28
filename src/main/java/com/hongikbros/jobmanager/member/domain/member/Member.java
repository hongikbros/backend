package com.hongikbros.jobmanager.member.domain.member;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import com.hongikbros.jobmanager.common.domain.BaseEntity;

@Entity
@Table(indexes = @Index(name = "email", columnList = "email"))
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
            String login) {
        if (AdminAccount.isAdmin(login)) {
            return new Member(null, oauthId, name, email, avatar, Role.ADMIN);
        }
        return new Member(null, oauthId, name, email, avatar, Role.USER);
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
