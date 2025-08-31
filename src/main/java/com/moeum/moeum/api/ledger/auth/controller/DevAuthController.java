package com.moeum.moeum.api.ledger.auth.controller;

import com.moeum.moeum.api.ledger.auth.dto.JwtResponseDto;
import com.moeum.moeum.api.ledger.user.repository.UserRepository;
import com.moeum.moeum.domain.User;
import com.moeum.moeum.global.security.JwtTokenProvider;
import com.moeum.moeum.type.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

@Profile("local") // 로컬에서만 활성화
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class DevAuthController {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    // POST /api/auth/dev-token?email=you@example.com&name=You
    @PostMapping("/dev-token")
    public JwtResponseDto issue(@RequestParam String email,
                                @RequestParam(defaultValue = "Dev User") String name) {
        User user = userRepository.findByEmail(email)
                .orElseGet(() -> userRepository.save(
                        User.builder().email(email).name(name).roleType(RoleType.ROLE_USER).build()
                ));
        return new JwtResponseDto(jwtTokenProvider.createToken(user));
    }
}