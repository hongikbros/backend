package com.hongikbros.jobmanager.member.application.oauth;

import java.io.Serializable;

import com.hongikbros.jobmanager.member.domain.member.Member;
import com.hongikbros.jobmanager.member.domain.member.Role;

public class SessionMember implements Serializable {
    private final Long id;
    private final String name;
    private final String email;
    private final String avatar;
    private final Role role;

    private SessionMember(Long id, String name, String email, String avatar,
            Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.avatar = avatar;
        this.role = role;
    }

    public static SessionMember of(Member member) {
        return new SessionMember(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getAvatar(),
                member.getRole());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAvatar() {
        return avatar;
    }

    public Role getRole() {
        return role;
    }
}
