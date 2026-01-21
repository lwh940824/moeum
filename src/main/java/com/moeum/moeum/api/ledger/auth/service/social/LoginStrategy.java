package com.moeum.moeum.api.ledger.auth.service.social;

import com.moeum.moeum.api.ledger.auth.dto.LoginRequestDto;
import com.moeum.moeum.domain.User;
import com.moeum.moeum.global.exception.CustomException;
import com.moeum.moeum.global.exception.ErrorCode;
import com.moeum.moeum.type.AuthProvider;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LoginStrategy {

    private final Map<AuthProvider, SocialAuthProvider> providerMap;

    public LoginStrategy(List<SocialAuthProvider> providers) {
        this.providerMap = providers.stream()
                .collect(Collectors.toMap(
                        SocialAuthProvider::provider,
                        provider -> provider
                ));
    }

    public User authenticate(LoginRequestDto request) {
        AuthProvider provider = AuthProvider.from(request.provider());
        SocialAuthProvider socialAuthProvider = providerMap.get(provider);

        if (socialAuthProvider == null) {
            throw new CustomException(ErrorCode.NOT_SUPPORT_LOGIN);
        }

        return socialAuthProvider.authenticate(request);
    }
}
