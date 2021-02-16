package com.hongikbros.jobmanager.security.oauth2;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.hongikbros.jobmanager.common.fixture.member.MemberFixture;
import com.hongikbros.jobmanager.common.fixture.oauth.AttributesFixture;
import com.hongikbros.jobmanager.member.domain.Member;
import com.hongikbros.jobmanager.member.domain.MemberRepository;
import com.hongikbros.jobmanager.member.domain.Role;

@ExtendWith(MockitoExtension.class)
class CustomOAuth2UserServiceTest {

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private DefaultOAuth2UserService defaultOAuth2UserService;
    @Mock
    private HttpSession httpSession;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private OAuth2UserRequest userRequest;
    @Mock
    private OAuth2User oAuth2User;

    private CustomOAuth2UserService customOAuth2UserService;

    @BeforeEach
    void setUp() {
        customOAuth2UserService = new CustomOAuth2UserService(memberRepository, httpSession,
                defaultOAuth2UserService);
    }

    @DisplayName("DefaultOAuth2UserService에서 Oauth2User를 불러와 Role이 User인 Member를 저장한다")
    @Test
    void should_saveOrUpdateMember_whenOauth2UserIsLoad() {
        // given
        Map<String, Object> attributes = AttributesFixture.ATTRIBUTES_FIXTURE;
        Member member = MemberFixture.MEMBER1;

        given(
                userRequest.getClientRegistration()
                        .getProviderDetails()
                        .getUserInfoEndpoint()
                        .getUserNameAttributeName()
        ).willReturn("id");
        given(defaultOAuth2UserService.loadUser(any())).willReturn(oAuth2User);
        given(oAuth2User.getAttributes()).willReturn(attributes);
        given(memberRepository.findByEmail(any())).willReturn(Optional.of(member));
        given(memberRepository.save(any())).willReturn(member);
        // when
        final OAuth2User defaultOAuth2User = customOAuth2UserService.loadUser(userRequest);
        final GrantedAuthority grantedAuthority = defaultOAuth2User.getAuthorities().stream()
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
        // then
        assertAll(
                () -> then(httpSession).should(times(2)).setAttribute(any(), any()),
                () -> assertThat(defaultOAuth2User.getAttributes()).containsValues(
                        1,
                        "EunSeok",
                        "test@test.com",
                        "avatar.url"),
                () -> assertThat(grantedAuthority.getAuthority()).contains(Role.USER.getKey())
        );
    }
}