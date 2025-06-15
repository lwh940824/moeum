package com.moeum.moeum.api.ledger.auth;

import com.moeum.moeum.api.ledger.auth.dto.GoogleLoginRequestDto;
import com.moeum.moeum.api.ledger.auth.dto.JwtResponseDto;
import com.moeum.moeum.api.ledger.user.UserRepository;
import com.moeum.moeum.domain.User;
import com.moeum.moeum.global.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public JwtResponseDto loginWithGoogle(GoogleLoginRequestDto googleLoginRequestDto) {
        String url = "https://www.googleapis.com/oauth2/v3/userinfo";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(googleLoginRequestDto.accessToken());
        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = new RestTemplate().exchange(url, HttpMethod.GET, httpEntity, Map.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Invalid Google Token");
        }

        Map userInfo = response.getBody();
        String email = (String) userInfo.get("email");
        String name = (String) userInfo.get("name");

        User user = userRepository.findByEmail(email)
                .orElseGet(() -> userRepository.save(User.builder().email(email).name(name).build()));

        String jwt = jwtTokenProvider.createToken(user);
        return new JwtResponseDto(jwt);
    }
}
