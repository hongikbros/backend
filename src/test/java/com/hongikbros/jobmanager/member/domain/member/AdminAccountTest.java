package com.hongikbros.jobmanager.member.domain.member;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class AdminAccountTest {

    @DisplayName("Admin 계정이면 true를 리턴한다")
    @ParameterizedTest
    @ValueSource(strings = {"joseph415", "JA960508"})
    void should_returnTrue_whenLoginIsAdminAccount(String expected) {
        assertThat(AdminAccount.isAdmin(expected)).isTrue();
    }

    @DisplayName("Admin 계정이 아니면 false를 리턴한다")
    @Test
    void should_returnFalse_whenLoginIsNotAdminAccount() {
        String expected = "user";
        assertThat(AdminAccount.isAdmin(expected)).isFalse();
    }
}