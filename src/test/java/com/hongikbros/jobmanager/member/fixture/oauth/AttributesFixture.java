package com.hongikbros.jobmanager.member.fixture.oauth;

import java.util.HashMap;
import java.util.Map;

public class AttributesFixture {
    public static final Map<String, Object> ATTRIBUTES_FIXTURE;

    static {
        ATTRIBUTES_FIXTURE = new HashMap<>();

        ATTRIBUTES_FIXTURE.put("id", 1);
        ATTRIBUTES_FIXTURE.put("name", "EunSeok");
        ATTRIBUTES_FIXTURE.put("email", "test@test.com");
        ATTRIBUTES_FIXTURE.put("avatar_url", "avatar.url");
    }
}
