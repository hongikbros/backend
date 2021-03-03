package com.hongikbros.jobmanager.member.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class RoleFactoryTest {

    @DisplayName("Admin 계정이면 Role.Admin 를 리턴한다")
    @ParameterizedTest
    @ValueSource(strings = {"joseph415", "JA960508"})
    void should_returnRole_ADMIN_whenLoginIsAdminAccount(String expected) {
        assertThat(RoleFactory.creatFrom(expected)).isEqualByComparingTo(Role.ADMIN);
    }

    @DisplayName("Admin 계정이 아니면 Role.User를 리턴한다")
    @Test
    void should_returnRole_USER_whenLoginIsNotAdminAccount() {
        String expected = "user";
        assertThat(RoleFactory.creatFrom(expected)).isEqualByComparingTo(Role.USER);
    }
}