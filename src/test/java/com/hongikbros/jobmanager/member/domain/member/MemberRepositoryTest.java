package com.hongikbros.jobmanager.member.domain.member;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.hongikbros.jobmanager.common.config.TestJpaAuditingConfig;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestJpaAuditingConfig.class)
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("email을 통해 Member를 가져온다")
    @Test
    void should_returnOptionalMember_whenEmailIsGiven() {
        // given
        String email = "admin@admin.com";
        memberRepository.save(Member.of("1L", "Test auditor", email, "test.avatar", "test login"));
        // when
        final Member member = memberRepository.findByEmail(email)
                .orElseThrow(IllegalArgumentException::new);
        // then
        assertAll(
                () -> assertThat(member.getId()).isEqualTo(1L),
                () -> assertThat(member.getOauthId()).isEqualTo("1L"),
                () -> assertThat(member.getName()).isEqualTo("Test auditor"),
                () -> assertThat(member.getEmail()).isEqualTo(email),
                () -> assertThat(member.getAvatar()).isEqualTo("test.avatar"),
                () -> assertThat(member.getRole()).isEqualTo(Role.USER),
                () -> assertThat(member.getCreatedBy()).isEqualTo("Test auditor"),
                () -> assertThat(member.getLastModifiedBy()).isEqualTo("Test auditor")
        );
    }
}