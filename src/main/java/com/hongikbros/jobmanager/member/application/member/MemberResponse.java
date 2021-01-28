package com.hongikbros.jobmanager.member.application.member;

public class MemberResponse {
    private final String name;
    private final String avatar;

    private MemberResponse(String name, String avatar) {
        this.name = name;
        this.avatar = avatar;
    }

    public static MemberResponse from(SessionMember sessionMember) {
        return new MemberResponse(sessionMember.getName(), sessionMember.getAvatar());
    }

    public String getName() {
        return name;
    }

    public String getAvatar() {
        return avatar;
    }
}
