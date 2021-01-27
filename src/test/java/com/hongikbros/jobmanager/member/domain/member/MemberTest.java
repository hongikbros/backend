package com.hongikbros.jobmanager.member.domain.member;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MemberTest {

    @DisplayName("AdminAccount 이면 Member의 Role은 ROLE_ADMIN 이다")
    @ParameterizedTest
    @ValueSource(strings = {"joseph415", "JA960508"})
    void should_returnAdminMember_whenRoleIsAdmin(String expected) {
        Member member = Member.of(1L, "EunSeok", "admin@admin.com", "test", expected);

        assertThat(member.getRole()).isEqualByComparingTo(Role.ADMIN);
    }

    @DisplayName("AdminAccount 가 아니면 Member의 Role은 ROLE_USER 이다")
    @Test
    void should_returnUserMember_whenRoleIsUser() {
        Member member = Member.of(1L, "EunSeok", "admin@admin.com", "test", "user");

        assertThat(member.getRole()).isEqualByComparingTo(Role.USER);
    }

    @DisplayName("이름과 아바타를 update 한다")
    @Test
    void should_updateMember_whenNameAndAvatarIsGiven() {
        // given
        Member member = Member.of(1L, "EunSeok", "admin@admin.com", "test", "user");
        String expectedName = "changingName";
        String expectedAvatar = "changingAvatar";
        // when
        member.update(expectedName, expectedAvatar);
        // then
        assertAll(
                () -> assertThat(member.getName()).isEqualTo(expectedName),
                () -> assertThat(member.getAvatar()).isEqualTo(expectedAvatar)
        );
    }
}