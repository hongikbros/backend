package com.hongikbros.jobmanager.common.utils;

import org.springframework.test.util.ReflectionTestUtils;

import com.hongikbros.jobmanager.member.domain.member.Member;

public class TestObjectUtils {
    public static Member createMember(Long id, String oauthId, String name, String email,
            String avatar, String login) {
        Member member = Member.of(oauthId, name, email, avatar, login);
        ReflectionTestUtils.setField(member, "id", id);

        return member;
    }
}
