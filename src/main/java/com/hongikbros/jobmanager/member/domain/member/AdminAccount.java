package com.hongikbros.jobmanager.member.domain.member;

import java.util.Arrays;

public enum AdminAccount {
    JOSEPH415,
    JA960508;

    public static boolean isAdmin(String login) {
        return Arrays.stream(AdminAccount.values())
                .anyMatch(
                        adminAccount -> adminAccount.name().equalsIgnoreCase(login));
    }
}
