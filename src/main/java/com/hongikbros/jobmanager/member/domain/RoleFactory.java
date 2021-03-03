package com.hongikbros.jobmanager.member.domain;

import java.util.Arrays;

public enum RoleFactory {
    JOSEPH415(Role.ADMIN),
    JA960508(Role.ADMIN),
    ELSE(Role.USER);

    private final Role role;

    RoleFactory(Role role) {
        this.role = role;
    }

    public static Role creatFrom(String loginId) {
        final RoleFactory account = Arrays.stream(RoleFactory.values())
                .filter(roleFactory -> roleFactory.name().equalsIgnoreCase(loginId))
                .findFirst()
                .orElse(ELSE);

        return account.role;
    }
}
