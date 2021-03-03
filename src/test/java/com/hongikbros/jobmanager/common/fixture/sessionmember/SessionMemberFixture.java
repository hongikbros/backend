package com.hongikbros.jobmanager.common.fixture.sessionmember;

import com.hongikbros.jobmanager.common.fixture.member.MemberFixture;
import com.hongikbros.jobmanager.common.utils.TestObjectUtils;
import com.hongikbros.jobmanager.member.domain.LoginMember;

public class SessionMemberFixture {
    public static LoginMember EUN_SEOK = TestObjectUtils.createSessionMember(
            MemberFixture.EUN_SEOK);
}
