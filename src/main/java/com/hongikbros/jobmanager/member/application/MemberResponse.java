package com.hongikbros.jobmanager.member.application;

import com.hongikbros.jobmanager.member.domain.LoginMember;

public class MemberResponse {

    private final String name;
    private final String avatar;

    private MemberResponse(String name, String avatar) {
        this.name = name;
        this.avatar = avatar;
    }

    public static MemberResponse from(LoginMember loginMember) {
        return new MemberResponse(loginMember.getName(), loginMember.getAvatar());
    }

    public String getName() {
        return name;
    }

    public String getAvatar() {
        return avatar;
    }
}
