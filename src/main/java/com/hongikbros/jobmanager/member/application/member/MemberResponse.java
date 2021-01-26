package com.hongikbros.jobmanager.member.application.member;

public class MemberResponse {
    private final String name;
    private final String avatar;

    public MemberResponse(String name, String avatar) {
        this.name = name;
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public String getAvatar() {
        return avatar;
    }
}
