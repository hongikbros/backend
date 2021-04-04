package com.hongikbros.jobmanager.common.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.test.util.ReflectionTestUtils;

import com.hongikbros.jobmanager.member.domain.LoginMember;
import com.hongikbros.jobmanager.member.domain.Member;
import com.hongikbros.jobmanager.notice.domain.company.Company;
import com.hongikbros.jobmanager.notice.domain.notice.ApplyUrl;
import com.hongikbros.jobmanager.notice.domain.notice.Duration;
import com.hongikbros.jobmanager.notice.domain.notice.Notice;
import com.hongikbros.jobmanager.security.oauth2.OAuthAttributes;

public class TestObjectUtils {
    public static Member createMember(Long id, Long oauthId, String name, String email,
            String avatar, String loginId) {
        Member member = Member.of(oauthId, name, email, avatar, loginId);
        ReflectionTestUtils.setField(member, "id", id);

        return member;
    }

    public static Notice createNotice(Long id, Long memberId, Company company, String title,
            Duration duration, ApplyUrl applyUrl) {
        Notice notice = Notice.of(memberId, company, title, duration, applyUrl);
        ReflectionTestUtils.setField(notice, "id", id);

        return notice;
    }

    public static Company createCompany(Long id, String icon) {
        Company company = Company.from(icon);
        ReflectionTestUtils.setField(company, "id", id);

        return company;
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
