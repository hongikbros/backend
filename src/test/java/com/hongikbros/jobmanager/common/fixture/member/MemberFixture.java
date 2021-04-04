package com.hongikbros.jobmanager.common.fixture.member;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.hongikbros.jobmanager.common.fixture.oauth.OAuth2AttributesFixture;
import com.hongikbros.jobmanager.common.utils.TestObjectUtils;
import com.hongikbros.jobmanager.member.domain.LoginMember;
import com.hongikbros.jobmanager.member.domain.LoginMemberAdapter;
import com.hongikbros.jobmanager.member.domain.Member;
import com.hongikbros.jobmanager.member.domain.Role;

public class MemberFixture {
    public static final Member MEMBER_EUN_SEOK = TestObjectUtils.createMember(1L, 1L, "EunSeok",
            "test@test.com",
            "test.url", "testLogin");

    public static final LoginMember LOGIN_MEMBER_EUNSEOK = TestObjectUtils.createSessionMember(
            MEMBER_EUN_SEOK);

    public static final LoginMemberAdapter LOGIN_MEMBER_ADAPTER = new LoginMemberAdapter(
            Collections.singletonList(new SimpleGrantedAuthority(Role.USER.getKey())),
            OAuth2AttributesFixture.O_AUTH_ATTRIBUTES.getAttributes(),
            OAuth2AttributesFixture.O_AUTH_ATTRIBUTES.getUserNameAttributeKey(),
            MemberFixture.LOGIN_MEMBER_EUNSEOK
    );
}
