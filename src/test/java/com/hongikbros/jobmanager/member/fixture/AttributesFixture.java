package com.hongikbros.jobmanager.member.fixture;

import java.util.HashMap;
import java.util.Map;

public class AttributesFixture {
    private final Map<String, Object> attribute = new HashMap<>();

    public AttributesFixture() {
        attribute.put("id", "1");
        attribute.put("name", "EunSeok");
        attribute.put("email", "test@test.com");
        attribute.put("avatar_url", "avatar.url");
    }

    public Map<String, Object> getAttribute() {
        return attribute;
    }
}
