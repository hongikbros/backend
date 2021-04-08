package com.hongikbros.jobmanager.common.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.test.util.ReflectionTestUtils;

import com.hongikbros.jobmanager.member.domain.LoginMember;
import com.hongikbros.jobmanager.member.domain.Member;
import com.hongikbros.jobmanager.notice.domain.notice.ApplyUrl;
import com.hongikbros.jobmanager.notice.domain.notice.Company;
import com.hongikbros.jobmanager.notice.domain.notice.Duration;
import com.hongikbros.jobmanager.notice.domain.notice.Notice;
import com.hongikbros.jobmanager.notice.domain.skill.Skill;
import com.hongikbros.jobmanager.security.oauth2.OAuthAttributes;

public class TestObjectUtils {
    public static Member createMember(Long id, Long oauthId, String name, String email,
            String avatar, String loginId) {
        Member member = Member.of(oauthId, name, email, avatar, loginId);
        ReflectionTestUtils.setField(member, "id", id);

        return member;
    }

    public static Notice createNotice(Long id, Long memberId, String title, Company company,
            List<Skill> skills,
            Duration duration, ApplyUrl applyUrl) {
        Notice notice = Notice.of(memberId, title, company, skills, duration, applyUrl);
        ReflectionTestUtils.setField(notice, "id", id);

        return notice;
    }

    public static List<Skill> createSkills(List<String> skillTags) {
        return skillTags.stream()
                .map(Skill::from)
                .collect(Collectors.toList());
    }

    public static LoginMember createSessionMember(Member member) {
        return LoginMember.of(member);
    }

    public static OAuthAttributes createOAuthAttributes() {
        Map<String, Object> attribute = new HashMap<>();
        attribute.put("id", 1);
        attribute.put("name", "EunSeok");
        attribute.put("email", "test@test.com");
        attribute.put("avatar_url", "avatar.url");

        return OAuthAttributes.of(attribute, "id");
    }
}
