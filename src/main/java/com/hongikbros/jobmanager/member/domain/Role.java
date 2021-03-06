package com.hongikbros.jobmanager.member.domain;

public enum Role {
    ADMIN("ROLE_ADMIN", "관리자"),
    USER("ROLE_USER", "일반 사용자"),
    GUEST("ROLE_GUEST", "비회원 사용자");

    private final String key;
    private final String title;

    Role(String key, String title) {
        this.key = key;
        this.title = title;
    }

    public boolean isLogin() {
        return !(this.equals(Role.GUEST));
    }

    public String getKey() {
        return key;
    }

    public String getTitle() {
        return title;
    }
}
