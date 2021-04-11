package com.hongikbros.jobmanager.member.domain;

import com.hongikbros.jobmanager.common.config.TestJpaAuditingConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestJpaAuditingConfig.class)
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("oauth id 를 통해 Member를 가져온다")
    @Test
    void should_returnOptionalMember_givenOauthIdIsGiven() {
        // given
        Long oauthId = 1L;
        memberRepository.save(Member.of(1L, "Test auditor", "admin@admin.com", "test.avatar", "test login"));
        // when
        final Member member = memberRepository.findByOauthId(oauthId)
                .orElseThrow(IllegalArgumentException::new);
        // then
        assertAll(
                () -> assertThat(member.getId()).isEqualTo(1L),
                () -> assertThat(member.getOauthId()).isEqualTo(1L),
                () -> assertThat(member.getName()).isEqualTo("Test auditor"),
                () -> assertThat(member.getEmail()).isEqualTo("admin@admin.com"),
                () -> assertThat(member.getAvatar()).isEqualTo("test.avatar"),
                () -> assertThat(member.getRole()).isEqualTo(Role.USER),
                () -> assertThat(member.getCreatedBy()).isEqualTo("Test auditor"),
                () -> assertThat(member.getLastModifiedBy()).isEqualTo("Test auditor")
        );
    }
}