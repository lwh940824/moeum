package com.moeum.moeum.api.ledger.auth.service.social;

import com.moeum.moeum.api.ledger.auth.dto.LoginRequestDto;
import com.moeum.moeum.domain.User;
import com.moeum.moeum.global.exception.CustomException;
import com.moeum.moeum.global.exception.ErrorCode;
import com.moeum.moeum.type.AuthProvider;
import com.moeum.moeum.type.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class GoogleAuthProvider implements SocialAuthProvider {

    @Value("${oauth.google.client-id}")
    private String clientId;
    @Value("${oauth.google.client-secret}")
    private String clientSecret;
    @Value("${oauth.google.redirect-uri}")
    private String redirectUri;

    private final RestTemplate restTemplate;

    @Override
    public AuthProvider provider() {
        return AuthProvider.GOOGLE;
    }

    @Override
    public User authenticate(LoginRequestDto request) {
        // google code 검증
        HttpHeaders codeHeaders = new HttpHeaders();
        codeHeaders.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("code", request.code());
        params.add("code_verifier", request.codeVerifier());
        params.add("redirect_uri", redirectUri);
        params.add("grant_type", "authorization_code");
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, codeHeaders);
        ResponseEntity<Map> response = restTemplate.postForEntity(
                "https://oauth2.googleapis.com/token",
                requestEntity,
                Map.class
        );

        Map codeBody = response.getBody();

        if (codeBody == null || codeBody.get("access_token") == null) {
            throw new CustomException(ErrorCode.INVALID_LOGIN);
        }

        String accessToken = codeBody.get("access_token").toString();

        // google user 정보
        HttpHeaders userHeaders = new HttpHeaders();
        userHeaders.setBearerAuth(accessToken);
        HttpEntity<Void> userRequest = new HttpEntity<>(userHeaders);
        ResponseEntity<Map> userResponse = restTemplate.exchange(
                "https://www.googleapis.com/oauth2/v3/userinfo",
                HttpMethod.GET,
                userRequest, Map.class
        );

        Map userBody = userResponse.getBody();
        if (userBody == null) {
            throw new CustomException(ErrorCode.INVALID_LOGIN);
        }

        return User.builder()
                .name(userBody.get("name").toString())
                .roleType(RoleType.ROLE_USER)
                .email(userBody.get("email").toString())
                .provider(request.provider())
                .build();
    }
}
