package com.hongikbros.jobmanager.common.auth;

import com.hongikbros.jobmanager.security.core.CurrentMember;

public class CurrentMemberAdapter {
    private final CurrentMember loginMember;

    public CurrentMemberAdapter(CurrentMember loginMember) {
        this.loginMember = loginMember;
    }

    public CurrentMember getSessionMember() {
        return loginMember;
    }
}
