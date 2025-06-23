package com.moeum.moeum.api.ledger.auth.controller;

import com.moeum.moeum.api.ledger.auth.service.AuthService;
import com.moeum.moeum.api.ledger.auth.dto.GoogleLoginRequestDto;
import com.moeum.moeum.api.ledger.auth.dto.JwtResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/google-login")
    public ResponseEntity<JwtResponseDto> googleLogin(@RequestBody GoogleLoginRequestDto googleLoginRequestDto) {
        JwtResponseDto jwtResponse = authService.loginWithGoogle(googleLoginRequestDto.code());
        return ResponseEntity.ok(jwtResponse);
    }
}