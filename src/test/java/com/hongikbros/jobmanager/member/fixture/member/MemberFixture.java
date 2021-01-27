package com.hongikbros.jobmanager.member.fixture.member;

import com.hongikbros.jobmanager.common.utils.TestObjectUtils;
import com.hongikbros.jobmanager.member.domain.member.Member;

public class MemberFixture {
    public static final Member MEMBER1 = TestObjectUtils.createMember(1L, 1L, "EunSeok",
            "test@test.com",
            "test.url", "testLogin");
}
