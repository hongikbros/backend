package com.hongikbros.jobmanager.common.auth;

import com.hongikbros.jobmanager.member.domain.LoginMember;

public class TestLoginMemberAdapter {
    private final LoginMember loginMember;

    public TestLoginMemberAdapter(LoginMember loginMember) {
        this.loginMember = loginMember;
    }

    public LoginMember getLoginMember() {
        return loginMember;
    }
}
