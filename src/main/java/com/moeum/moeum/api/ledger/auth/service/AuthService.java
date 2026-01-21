package com.moeum.moeum.api.ledger.auth.service;

import com.moeum.moeum.api.ledger.auth.dto.GoogleLoginRequestDto;
import com.moeum.moeum.api.ledger.auth.dto.JwtResponseDto;
import com.moeum.moeum.api.ledger.auth.dto.LoginRequestDto;
import com.moeum.moeum.api.ledger.auth.service.social.LoginStrategy;
import com.moeum.moeum.api.ledger.user.service.UserService;
import com.moeum.moeum.domain.User;
import com.moeum.moeum.global.security.JwtTokenProvider;
import com.moeum.moeum.type.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final LoginStrategy loginStrategy;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${oauth.google.client-id}")
    private String clientId;

    @Value("${oauth.google.client-secret}")
    private String clientSecret;

    @Value("${oauth.google.redirect-uri}")
    private String redirectUri;

    public JwtResponseDto loginWithGoogle(GoogleLoginRequestDto googleLoginRequestDto) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", googleLoginRequestDto.code());
        params.add("code_verifier", googleLoginRequestDto.codeVerifier());
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("grant_type", "authorization_code");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<Map> tokenResponse = restTemplate.postForEntity(
                "https://oauth2.googleapis.com/token",
                request,
                Map.class
        );

        Map tokenBody = tokenResponse.getBody();
        String accessToken = (String) tokenBody.get("access_token");

        // Google userinfo API
        HttpHeaders userHeaders = new HttpHeaders();
        userHeaders.setBearerAuth(accessToken);
        HttpEntity<Void> userRequest = new HttpEntity<>(userHeaders);

        ResponseEntity<Map> userResponse = restTemplate.exchange(
                "https://www.googleapis.com/oauth2/v3/userinfo",
                HttpMethod.GET,
                userRequest,
                Map.class
        );

        Map userInfo = userResponse.getBody();
        String email = (String) userInfo.get("email");
        String name = (String) userInfo.get("name");

        User user = userService.findUserByEmail(email)
                .orElseGet(() -> userService.createUser(User.builder().email(email).name(name).roleType(RoleType.ROLE_USER).build()));

        String jwt = jwtTokenProvider.createToken(user);
        return new JwtResponseDto(jwt);
    }

    public JwtResponseDto login(LoginRequestDto loginRequestDto) {
        User user = loginStrategy.authenticate(loginRequestDto);

        User findUser = userService.findUserByEmail(user.getEmail())
                .orElseGet(() -> userService.createUser(
                        User.builder()
                                .name(user.getName())
                                .roleType(user.getRoleType())
                                .email(user.getEmail())
                                .provider(user.getProvider())
                                .build()));

        String jwt = jwtTokenProvider.createToken(findUser);
        return new JwtResponseDto(jwt);
    }
}