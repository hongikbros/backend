package com.hongikbros.jobmanager.member.application.oauth;

import java.util.Map;

import com.hongikbros.jobmanager.member.domain.member.Member;

public class OAuthAttributes {
    private final Map<String, Object> attributes;
    private final String nameAttributeKey;
    private final String oauthId;
    private final String name;
    private final String email;
    private final String avatar;

    private OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey,
            String oauthId, String name, String email, String avatar) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.oauthId = oauthId;
        this.name = name;
        this.email = email;
        this.avatar = avatar;
    }

    public static OAuthAttributes of(Map<String, Object> attributes, String userNameAttributeName) {
        return new OAuthAttributes(
                attributes,
                userNameAttributeName,
                (String)attributes.get("id"),
                (String)attributes.get("name"),
                (String)attributes.get("email"),
                (String)attributes.get("avatar_url")
        );
    }

    public Member toEntity() {
        return Member.of(oauthId, name, email, avatar, (String)attributes.get("login"));
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public String getNameAttributeKey() {
        return nameAttributeKey;
    }

    public String getOauthId() {
        return oauthId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAvatar() {
        return avatar;
    }
}