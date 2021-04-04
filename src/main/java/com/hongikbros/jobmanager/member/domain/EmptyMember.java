package com.hongikbros.jobmanager.member.domain;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import com.hongikbros.jobmanager.security.core.CurrentMember;

@Lazy
@RequestScope
@Component
public class EmptyMember implements CurrentMember {

    private static final String EMPTY = "EMPTY";

    @Override
    public boolean isLogin() {
        return false;
    }

    @Override
    public Long getId() {
        return null;
    }

    @Override
    public String getName() {
        return EMPTY + "_NAME";
    }

    @Override
    public String getEmail() {
        return EMPTY + "_Email";
    }

    @Override
    public String getAvatar() {
        return EMPTY + "_Avatar";
    }

    @Override
    public Role getRole() {
        return Role.GUEST;
    }

}
