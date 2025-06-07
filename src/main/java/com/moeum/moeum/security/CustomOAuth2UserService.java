package com.moeum.moeum.security;

import com.moeum.moeum.api.ledger.User.UserRepository;
import com.moeum.moeum.domain.User;
import com.moeum.moeum.type.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);

        // 1. Provider 정보 추출
        String provider = userRequest.getClientRegistration().getRegistrationId(); // "google"
        String userNameAttribute = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName(); // 보통 "sub"

        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");
        String providerId = (String) attributes.get(userNameAttribute);

        // 2. 사용자 DB 조회
        User user = userRepository.findByEmailAndProvider(email, provider)
                .orElseGet(() -> userRepository.save(User.builder()
                        .activate_id(email)
                        .email(email)
                        .roleType(RoleType.ADMIN)
                        .provider(provider)
                        .providerId(providerId)
                        .build()));

        String token = jwtTokenProvider.createToken(user.getEmail());

        // 4. OAuth2User 반환
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                attributes,
                "sub"
        );
    }
}
