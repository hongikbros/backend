package com.hongikbros.jobmanager.security.oauth2;

import java.util.Collections;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hongikbros.jobmanager.member.domain.LoginMember;
import com.hongikbros.jobmanager.member.domain.LoginMemberAdapter;
import com.hongikbros.jobmanager.member.domain.Member;
import com.hongikbros.jobmanager.member.domain.MemberRepository;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    public static final String PRINCIPAL_OAUTHID_BEFORE_SAVING = "principalNameBeforeSaving";

    private final MemberRepository memberRepository;
    private final HttpSession httpSession;
    private final OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate;

    public CustomOAuth2UserService(
            MemberRepository memberRepository,
            HttpSession httpSession,
            @Qualifier("defaultOAuth2UserService") OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate) {
        this.memberRepository = memberRepository;
        this.httpSession = httpSession;
        this.delegate = delegate;
    }

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(oAuth2User.getAttributes(),
                userNameAttributeName);
        Member member = saveOrUpdate(attributes);

        return new LoginMemberAdapter(
                Collections.singletonList(new SimpleGrantedAuthority(member.getRole().getKey())),
                attributes.getAttributes(),
                attributes.getUserNameAttributeKey(),
                LoginMember.of(member)
        );
    }

    private Member saveOrUpdate(OAuthAttributes attributes) {
        httpSession.setAttribute(PRINCIPAL_OAUTHID_BEFORE_SAVING, attributes.getName());

        Member member = memberRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getAvatar()))
                .orElse(attributes.toEntity());

        return memberRepository.save(member);
    }
}
