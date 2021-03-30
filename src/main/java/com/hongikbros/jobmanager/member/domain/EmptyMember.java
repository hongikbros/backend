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
    private static final long EMPTY_ID = -1000L;

    @Override
    public boolean isLogin() {
        return false;
    }

    @Override
    public Long getId() {
        return EMPTY_ID;
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
