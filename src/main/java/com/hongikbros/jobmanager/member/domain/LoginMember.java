package com.hongikbros.jobmanager.member.domain;

import com.hongikbros.jobmanager.security.core.CurrentMember;

public class LoginMember implements CurrentMember {
    private final Long id;
    private final String name;
    private final String email;
    private final String avatar;
    private final Role role;

    private LoginMember(Long id, String name, String email, String avatar,
            Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.avatar = avatar;
        this.role = role;
    }

    public static LoginMember of(Member member) {
        return new LoginMember(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getAvatar(),
                member.getRole());
    }

    public boolean isLogin() {
        return true;
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
