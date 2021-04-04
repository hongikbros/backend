package com.hongikbros.jobmanager.member.domain;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

public class LoginMemberAdapter extends DefaultOAuth2User {
    private final LoginMember loginMember;

    public LoginMemberAdapter(
            Collection<? extends GrantedAuthority> authorities,
            Map<String, Object> attributes, String nameAttributeKey,
            LoginMember loginMember) {
        super(authorities, attributes, nameAttributeKey);
        this.loginMember = loginMember;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        LoginMemberAdapter that = (LoginMemberAdapter)o;
        return Objects.equals(loginMember, that.loginMember);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), loginMember);
    }

    public LoginMember getLoginMember() {
        return loginMember;
    }
}
