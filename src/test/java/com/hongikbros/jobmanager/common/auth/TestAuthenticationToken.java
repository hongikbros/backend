package com.hongikbros.jobmanager.common.auth;

import com.hongikbros.jobmanager.member.domain.LoginMember;

public class TestAuthenticationToken {
    private final LoginMember loginMember;

    public TestAuthenticationToken(LoginMember loginMember) {
        this.loginMember = loginMember;
    }

    public LoginMember getSessionMember() {
        return loginMember;
    }
}
