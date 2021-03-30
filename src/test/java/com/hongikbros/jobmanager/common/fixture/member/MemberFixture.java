package com.hongikbros.jobmanager.common.fixture.member;

import com.hongikbros.jobmanager.common.utils.TestObjectUtils;
import com.hongikbros.jobmanager.member.domain.LoginMember;
import com.hongikbros.jobmanager.member.domain.Member;

public class MemberFixture {
    public static final Member MEMBER_EUN_SEOK = TestObjectUtils.createMember(1L, 1L, "EunSeok",
            "test@test.com",
            "test.url", "testLogin");

    public static final LoginMember LOGIN_MEMBER_EUNSEOK = TestObjectUtils.createSessionMember(
            MEMBER_EUN_SEOK);
}
