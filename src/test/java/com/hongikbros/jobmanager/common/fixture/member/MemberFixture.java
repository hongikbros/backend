package com.hongikbros.jobmanager.common.fixture.member;

import com.hongikbros.jobmanager.common.utils.TestObjectUtils;
import com.hongikbros.jobmanager.member.domain.Member;
import com.hongikbros.jobmanager.security.core.CurrentMember;

public class MemberFixture {
    public static final Member EUN_SEOK = TestObjectUtils.createMember(1L, 1L, "EunSeok",
            "test@test.com",
            "test.url", "testLogin");

    public static final CurrentMember SESSION_MEMBER_EUNSEOK = TestObjectUtils.createSessionMember(
            EUN_SEOK);
}
